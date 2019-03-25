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
    internal fun alwaysOpen() {
        val forEntity = rst.getForEntity("http://localhost:8080/open/11/11", DoorReaction::class.java)
        Assertions.assertThat(forEntity.statusCodeValue).isEqualTo(200)
        Assertions.assertThat(forEntity.body).isEqualTo(DoorReaction("11", DoorAction.OPEN))
    }
}