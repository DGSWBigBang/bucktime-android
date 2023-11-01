package org.qpeterp.timebucks.dataClass

data class CafeReservation(
    val deskIdx: Int,
    val deskName: String,
    val finishTime: String,
    val rezIdx: Int,
    val startTime: String,
    val used: String,
    val userMail: String
)