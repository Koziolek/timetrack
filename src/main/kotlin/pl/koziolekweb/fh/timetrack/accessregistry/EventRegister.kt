package pl.koziolekweb.fh.timetrack.accessregistry

import org.springframework.stereotype.Component
import pl.koziolekweb.fh.timetrack.api.DoorAction
import pl.koziolekweb.fh.timetrack.tools.ImmutableList
import pl.koziolekweb.fh.timetrack.tools.toImmutableList
import java.time.LocalDateTime
import java.util.*

interface EventRegister {

    fun registerEvent(cardId: String, action: DoorAction):Event
    fun findLastOpenByCardId(it: String): Optional<Event>
    fun all():List<Event>
}

@Component
class EventRegisterInMemory : EventRegister {

    private val events: MutableList<Event> = mutableListOf()

    override fun findLastOpenByCardId(cardId: String): Optional<Event> {
        return Optional.ofNullable(events
                .filter { it.cardId == cardId }
                .filter { it.action == DoorAction.OPEN }
                .maxBy { it.ts })


    }

    override fun registerEvent(cardId: String, action: DoorAction):Event {
        val element = Event(cardId, action)
        events.add(element)
        return element
    }

    override fun all(): List<Event> {
        return events.toImmutableList()
    }
}

data class Event(val cardId: String, val action: DoorAction, val ts: LocalDateTime = LocalDateTime.now())