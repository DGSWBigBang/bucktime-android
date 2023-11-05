package org.qpeterp.timebucks

data class CafeTableResponseItem(
    val cafeIdx: Int,
    val capacity: Int,
    val deskIdx: Int,
    val deskName: String,
    val price: Int
)