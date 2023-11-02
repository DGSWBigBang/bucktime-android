package org.qpeterp.timebucks

data class LoginResponse(
    val accessToken: String,
    val grantType: String,
    val refreshToken: String
)