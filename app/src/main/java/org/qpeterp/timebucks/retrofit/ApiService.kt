package org.qpeterp.timebucks.retrofit

import org.qpeterp.timebucks.CafeInfo
import retrofit2.Call
import retrofit2.http.GET
import java.util.ArrayList

interface ApiService {
    @GET("/cafe/show")
    fun getCafeData(
    ): Call<ApiRespense<ArrayList<CafeInfo>>>

}
