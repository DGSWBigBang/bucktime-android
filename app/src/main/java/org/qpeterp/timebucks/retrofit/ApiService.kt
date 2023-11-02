package org.qpeterp.timebucks.retrofit

import org.qpeterp.timebucks.dataClass.CafeInfo
import org.qpeterp.timebucks.dataClass.CafeReservation
import org.qpeterp.timebucks.JoinRequest
import org.qpeterp.timebucks.LoginRequest
import org.qpeterp.timebucks.LoginResponse
import org.qpeterp.timebucks.StringResponse
import org.qpeterp.timebucks.dataClass.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import kotlin.collections.ArrayList
import retrofit2.http.POST

interface ApiService {
    @GET("/cafe/show")
    fun getCafeData(
    ): Call<ArrayList<CafeInfo>>

    @GET("/rez/show")
    fun getReservation(
    ): Call<ArrayList<CafeReservation>>

    @POST("/user/login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST("/user/create")
    fun create(
        @Body joinRequest: JoinRequest
    ): Call<StringResponse>

    @GET("/user/show")
    fun getUserData(
    ): Call<UserInfo>
}
