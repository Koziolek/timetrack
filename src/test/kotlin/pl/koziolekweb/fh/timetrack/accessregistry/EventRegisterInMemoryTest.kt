package pl.koziolekweb.fh.timetrack.accessregistry

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.koziolekweb.fh.timetrack.api.DoorAction

internal class EventRegisterInMemoryTest {
    private lateinit var erim: EventRegisterInMemory

    @BeforeEach
    internal fun setUp() {
        this.erim = EventRegisterInMemory()
    }

    @Test
    internal fun registrationOfOpenEventIsSearchable() {
        erim.registerEvent("11", DoorAction.OPEN)
        val event = erim.findLastOpenByCardId("11")
        Assertions.assertThat(event).isPresent
    }
    @Test
    internal fun registrationOfNonOpenEventIsSearchable() {
        erim.registerEvent("11", DoorAction.ALERT)
        val event = erim.findLastOpenByCardId("11")
        Assertions.assertThat(event).isEmpty
    }
}