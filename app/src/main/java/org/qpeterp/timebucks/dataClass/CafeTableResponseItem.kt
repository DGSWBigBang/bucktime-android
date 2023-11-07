package org.qpeterp.timebucks.dataClass

data class CafeTableResponseItem(
    val deskIdx: Int,
    val deskName: String,
    val cafeIdx: Int,
    val price: Int,
    val capacity: Int
)