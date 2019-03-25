package pl.koziolekweb.fh.timetrack.api

import java.time.LocalDateTime

enum class DoorAction {
    OPEN, BLOCK, ALERT
}

data class DoorReaction(val doorId: String, val doorAction: DoorAction)

data class CardInList(val users: List<CardIn>)

data class CardIn(val cardId: String, val enterTime: LocalDateTime)

data class EventView(val cardId: String, val eventType: DoorAction, val eventTime: LocalDateTime)