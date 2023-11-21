package org.qpeterp.timebucks.dataClass

data class CafeReservation(
    val rezIdx: Int,
    val deskName: String,
    val startTime: String,
    val finishTime: String,
    val userMail: String
)