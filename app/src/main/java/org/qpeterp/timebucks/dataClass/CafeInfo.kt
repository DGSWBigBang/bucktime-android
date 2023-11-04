package org.qpeterp.timebucks.dataClass

data class CafeInfo(
    val address: String,
    val cafeDescription: String,
    val cafeIdx: Int,
    val cafeName: String,
    val ownerMail: String,
    val latitude: String,
    val longitude: String,
    val callNumber: String,
    val openTime: String,
    val closeTime: String
)