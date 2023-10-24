package org.qpeterp.timebucks.retrofit

import android.util.Log
import android.widget.Toast
import org.qpeterp.timebucks.CafeInfo
import org.qpeterp.timebucks.MainMainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RequestManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://timebucks.kro.kr")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    fun getCafeInfo(callback: (ApiRespense<ArrayList<CafeInfo>>) -> Unit) {
        val call = apiService.getCafeData()

        call.enqueue(object : Callback<ApiRespense<ArrayList<CafeInfo>>> {
            override fun onResponse(call: Call<ApiRespense<ArrayList<CafeInfo>>>, response: Response<ApiRespense<ArrayList<CafeInfo>>>) {
                if (response.isSuccessful) {
                    // 날씨 데이터를 처리하는 코드를 작성

                    val apiResponse = response.body()
                    val data = apiResponse

                    callback(data!!)

                } else {
                    // 에러 처리
                    Log.d("resdataError","${response.code()}")
                }
            }

            override fun onFailure(call: Call<ApiRespense<ArrayList<CafeInfo>>>, t: Throwable) {
                // 통신 실패 처리
                Log.d("errorrrrr", "${t}, ${call}")
            }
        })
    }
}