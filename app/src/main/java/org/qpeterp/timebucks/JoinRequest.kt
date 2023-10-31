package org.qpeterp.timebucks

data class JoinRequest(
    val phoneNumber: String,
    val userMail: String,
    val userName: String,
    val userPassword: String
)