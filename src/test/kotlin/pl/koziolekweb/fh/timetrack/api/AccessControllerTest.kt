package pl.koziolekweb.fh.timetrack.api

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
internal class AccessControllerTest(
        @Autowired var rst: TestRestTemplate
) {


    @Test
    internal fun enterThenExit() {
        val openDoor = rst.getForEntity("http://localhost:8080/open/11/11", DoorReaction::class.java)
        Assertions.assertThat(openDoor.statusCodeValue).isEqualTo(200)
        Assertions.assertThat(openDoor.body).isEqualTo(DoorReaction("11", DoorAction.OPEN))

        val cardsIn = rst.getForEntity("http://localhost:8080/usersIn", List::class.java)
        Assertions.assertThat(cardsIn.statusCodeValue).isEqualTo(200)
        Assertions.assertThat(cardsIn.body).hasSize(1)

        val closeDoor = rst.getForEntity("http://localhost:8080/open/11/11", DoorReaction::class.java)
        Assertions.assertThat(closeDoor.statusCodeValue).isEqualTo(200)
        Assertions.assertThat(closeDoor.body).isEqualTo(DoorReaction("11", DoorAction.OPEN))

        val cardsInAfterExit = rst.getForEntity("http://localhost:8080/usersIn", List::class.java)
        Assertions.assertThat(cardsInAfterExit.statusCodeValue).isEqualTo(200)
        Assertions.assertThat(cardsInAfterExit.body).hasSize(0)


    }
}