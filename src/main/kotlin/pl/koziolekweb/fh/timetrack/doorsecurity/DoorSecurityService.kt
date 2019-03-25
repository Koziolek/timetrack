package pl.koziolekweb.fh.timetrack.doorsecurity

import org.springframework.stereotype.Service
import pl.koziolekweb.fh.timetrack.api.DoorAction
import kotlin.random.Random

/**
 * Send information about door action is it be open, blocked or alert security.
 */
interface DoorSecurityService {
    fun unlockDoor(doorId: String, cardId: String): DoorAction
}

@Service
class DoorSecurityServiceImpl : DoorSecurityService {

    override fun unlockDoor(doorId: String, cardId: String): DoorAction {
        if (doorId.endsWith("1") && cardId.endsWith("1"))
            return DoorAction.OPEN
        else {
            val alert = Random.nextInt(0, 100)
            return when (alert) {
                in 0..10 -> DoorAction.ALERT
                in 11..25 -> DoorAction.OPEN
                else -> DoorAction.BLOCK
            }

        }

    }
}