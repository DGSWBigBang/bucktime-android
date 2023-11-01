package org.qpeterp.timebucks.retrofit

import org.qpeterp.timebucks.dataClass.CafeInfo
import org.qpeterp.timebucks.dataClass.CafeReservation
import retrofit2.Call
import retrofit2.http.GET
import kotlin.collections.ArrayList

interface ApiService {
    @GET("/cafe/show")
    fun getCafeData(
    ): Call<ArrayList<CafeInfo>>

    @GET("/rez/show")
    fun getReservation(
    ): Call<ArrayList<CafeReservation>>

//    @GET("/cafe/show")
//    fun getCafeData(
//    ): Call<ApiRespense<ArrayList<CafeInfo>>>

//    @GET("/rez/show")
//    fun getReservation(
//    ): Call<ApiRespense<ArrayList<CafeReservation>>>
}
