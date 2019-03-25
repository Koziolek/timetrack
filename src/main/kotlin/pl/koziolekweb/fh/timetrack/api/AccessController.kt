package pl.koziolekweb.fh.timetrack.api

import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import pl.koziolekweb.fh.timetrack.accessregistry.AccessRegistryService
import pl.koziolekweb.fh.timetrack.doorsecurity.DoorSecurityService

@RestController
class AccessController(
        @Autowired val dss: DoorSecurityService,
        @Autowired val ars: AccessRegistryService,
        @Autowired val log: Logger
) {

    @GetMapping("/open/{doorId}/{cardId}")
    fun openDoor(@PathVariable doorId: String,
                 @PathVariable cardId: String): DoorReaction {
        val doorAction = dss.unlockDoor(doorId, cardId)
        log.info(ars.cardChange(cardId, doorAction).toString())
        return DoorReaction(doorId, doorAction)
    }
}