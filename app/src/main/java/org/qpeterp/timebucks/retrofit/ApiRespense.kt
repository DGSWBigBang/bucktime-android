package org.qpeterp.timebucks.retrofit

data class ApiRespense<T>(
    val success : Boolean,
    val response: T?,
    val error: String
) {
    constructor() : this(false, null, "")
}
