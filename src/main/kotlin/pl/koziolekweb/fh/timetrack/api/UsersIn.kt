package pl.koziolekweb.fh.timetrack.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import pl.koziolekweb.fh.timetrack.accessregistry.AccessRegistryService

@RestController
class UsersIn(
        @Autowired val ars: AccessRegistryService
) {

    @GetMapping("/usersIn")
    fun usersIn():Any{
        return ars.cardsIn()
    }

    @GetMapping("/usersOut")
    fun usersOut():Any{
        return ars.cardsOut()
    }
}