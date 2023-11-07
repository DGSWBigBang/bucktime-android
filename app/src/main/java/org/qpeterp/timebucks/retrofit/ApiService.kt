package org.qpeterp.timebucks.retrofit

import org.qpeterp.timebucks.dataClass.CafeTableResponse
import org.qpeterp.timebucks.dataClass.CafeInfo
import org.qpeterp.timebucks.dataClass.CafeReservation
import org.qpeterp.timebucks.JoinRequest
import org.qpeterp.timebucks.LoginRequest
import org.qpeterp.timebucks.LoginResponse
import org.qpeterp.timebucks.ReservationRequest
import org.qpeterp.timebucks.StringResponse
import org.qpeterp.timebucks.dataClass.MenuData
import org.qpeterp.timebucks.dataClass.MenuListDataItem
import org.qpeterp.timebucks.dataClass.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import kotlin.collections.ArrayList
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("/cafe/show")
    fun getCafeData(
    ): Call<ArrayList<CafeInfo>>

    @GET("/rez/show")
    fun getReservation(
    ): Call<CafeReservation>

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

    @GET("/desk/show")
    fun showCafeDesk(
        @Query("cafe-idx") cafeIdx: Int,
    ): Call<CafeTableResponse>
    @GET("/menu/show")
    fun getMenuListData(
        @Query("cafe-idx") cafeIdx: Int
    ): Call<ArrayList<MenuListDataItem>>

    @GET("/menu/show")
    fun getMenuData(
        @Query("menu-idx") menuIdx: Int
    ): Call<MenuData>

    @POST("/rez/create")
    fun reservationCreate(
        @Body reservationRequest: ReservationRequest,
        @Header("Authorization") token: String
    ): Call<StringResponse>

//    @GET("/rez/show/user")
//    fun rezShowUser(
//
//    )

    @POST("/order/create")
    fun postOrder(
        @Query("menu-idx") menuIdx: Int,
        @Header("Authorization") token: String
    ): Call<String>
}
