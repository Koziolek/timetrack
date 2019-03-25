package pl.koziolekweb.fh.timetrack.accessregistry

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.koziolekweb.fh.timetrack.api.CardIn
import pl.koziolekweb.fh.timetrack.api.DoorAction
import pl.koziolekweb.fh.timetrack.api.DoorAction.*
import pl.koziolekweb.fh.timetrack.api.EventView
import pl.koziolekweb.fh.timetrack.tools.ImmutableList
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * This is an access point to information about actual state of area.
 */
interface AccessRegistryService {

    /**
     * List all cards that are IN the area.
     */
    fun cardsIn(): List<CardIn>

    /**
     * List all cards that are OUT the area.
     */
    fun cardsOut(): List<String>

    /**
     * Register card state change. Default state is OUT. Rules:
     *
     * Card OUT & DoorAction.OPEN → Card IN
     * Card IN & DoorAction.OPEN → Card OUT
     * Card XXXX & DoorAction.{BLOCK, ALERT} → Card XXXX
     *
     * Additionally register all events in storage
     * @return information about event
     */
    fun cardChange(cardId: String, action: DoorAction): EventView

}

@Component
class AccessRegistryServiceImpl(
        @Autowired val eventRegister: EventRegister
) : AccessRegistryService {

    /**
     * Need that lock to protect data changes
     */
    val lock: ReentrantReadWriteLock = ReentrantReadWriteLock()
    val cardsIn: MutableList<String> = arrayListOf()
    val cardsOut: MutableList<String> = arrayListOf()

    override fun cardsIn(): List<CardIn> {
        val cin = asImmutable(cardsIn)
        return cin.map {
            eventRegister.findLastOpenByCardId(it)
                    .map {
                        CardIn(it.cardId, it.ts)
                    }
        }.filter {
            it.isPresent
        }.map {
            it.get()
        }
    }

    override fun cardsOut(): List<String> {
        return asImmutable(cardsOut)
    }

    private fun <T> asImmutable(i: MutableList<T>): ImmutableList<T> {
        lock.readLock().lock()
        val o = ImmutableList(i)
        lock.readLock().unlock()
        return o
    }

    override fun cardChange(cardId: String, action: DoorAction): EventView {
        val (cid, act, ts) = when (action) {
            ALERT, BLOCK -> {
                eventRegister.registerEvent(cardId, action)
            }
            OPEN -> {
                lock.writeLock().lock()
                // initial state is undefined
                if (!cardsOut.contains(cardId) && !cardsIn.contains(cardId)) {
                    cardsIn.add(cardId)
                } else {
                    if (cardsOut.contains(cardId)) {
                        cardsOut.remove(cardId)
                        cardsIn.add(cardId)
                    } else {
                        cardsIn.remove(cardId)
                        cardsOut.add(cardId)
                    }
                }
                lock.writeLock().unlock()
                eventRegister.registerEvent(cardId, action)
            }
        }

        return EventView(cid, act, ts)

    }
}

