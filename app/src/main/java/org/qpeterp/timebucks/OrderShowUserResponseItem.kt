package org.qpeterp.timebucks

data class OrderShowUserResponseItem(
    val cafeName: String,
    val completion: String,
    val menuName: String,
    val menuPrice: Int,
    val orderIdx: Int,
    val orderTime: String,
    val userName: String
)