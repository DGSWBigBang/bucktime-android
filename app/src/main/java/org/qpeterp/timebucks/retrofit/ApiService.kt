package org.qpeterp.timebucks.retrofit

import org.qpeterp.timebucks.CafeInfo
import org.qpeterp.timebucks.JoinRequest
import org.qpeterp.timebucks.LoginRequest
import org.qpeterp.timebucks.LoginResponse
import org.qpeterp.timebucks.StringResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.ArrayList

interface ApiService {
    @GET("/cafe/show")
    fun getCafeData(
    ): Call<ApiRespense<ArrayList<CafeInfo>>>

    @POST("/user/login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>

    @POST("/user/create")
    fun create(
        @Body joinRequest: JoinRequest
    ): Call<StringResponse>
}
