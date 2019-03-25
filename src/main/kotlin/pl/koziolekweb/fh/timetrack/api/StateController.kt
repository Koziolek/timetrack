package pl.koziolekweb.fh.timetrack.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.koziolekweb.fh.timetrack.accessregistry.AccessRegistryService
import pl.koziolekweb.fh.timetrack.accessregistry.EventRegister

@RestController
class StateController(
        @Autowired val ars: AccessRegistryService,
        @Autowired val er: EventRegister
) {

    @GetMapping("/usersIn")
    fun usersIn():List<CardIn>{
        return ars.cardsIn()
    }

    @GetMapping("/usersOut")
    fun usersOut():Any{
        return ars.cardsOut()
    }

    @GetMapping("/events")
    fun events():Any{
        return er.all().map {
            EventView(it.cardId, it.action, it.ts)
        }
    }
}