package pl.koziolekweb.fh.timetrack.doorsecurity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import pl.koziolekweb.fh.timetrack.api.DoorAction

internal class DoorSecurityServiceImplTest{

    private lateinit var dssi: DoorSecurityServiceImpl

    @BeforeEach
    internal fun setUp() {
        dssi = DoorSecurityServiceImpl()
    }

    @Test
    internal fun alwaysOpen() {
        val unlockDoor = dssi.unlockDoor("11", "21")
        assertEquals(unlockDoor, DoorAction.OPEN)
    }

    @Test
    internal fun randomlyOpen() {
        // always return something but randomly
        val unlockDoor = dssi.unlockDoor("11", "22")
        assertTrue(DoorAction.values().contains(unlockDoor))
    }
}