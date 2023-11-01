package org.qpeterp.timebucks.retrofit

import android.util.Log
import org.qpeterp.timebucks.dataClass.CafeInfo
import org.qpeterp.timebucks.dataClass.CafeReservation
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

    fun getCafeInfo(callback: (ArrayList<CafeInfo>) -> Unit) {
        val call = apiService.getCafeData()

        call.enqueue(object : Callback<ArrayList<CafeInfo>> {
            override fun onResponse(call: Call<ArrayList<CafeInfo>>, response: Response<ArrayList<CafeInfo>>) {
                Log.d("getCafeInfo is Sucessful!_0", "$response")
                if (response.isSuccessful) {
                    Log.d("getCafeInfo is Sucessful!_1", "$response")
                    val apiResponse = response.body() ?: return
                    Log.d("getCafeInfo is Sucessful!_2", "$response")

                    callback(apiResponse)
                }
                Log.d("if문 작동 X", "$response")

            }
            override fun onFailure(call: Call<ArrayList<CafeInfo>>, t: Throwable) {
                // 통신 실패 처리
                Log.d("errorrrrr", "${t}, ${call}")
            }
        })
    }

    fun getReservation(callback: (ArrayList<CafeReservation>) -> Unit) {
        val call = apiService.getReservation()
        Log.d("requManager getReservation", "successful!")

        call.enqueue(object : Callback<ArrayList<CafeReservation>> {
            override fun onResponse(call: Call<ArrayList<CafeReservation>>, response: Response<ArrayList<CafeReservation>>) {
                Log.d("response is Successful_1", "successful!")
                val apiResponse = response.body() ?: return
                Log.d("response is Successful_2", "successful!")

                callback(apiResponse)
                Log.d("callback success", "successful!")

            }
            override fun onFailure(call: Call<ArrayList<CafeReservation>>, t: Throwable) {
                // 통신 실패 처리
                Log.d("errorrrrr", "${t}, $call")
            }
        })

        Log.d("하하 안되노", "시시히히!")

    }


//    fun getCafeInfo(callback: (ApiRespense<ArrayList<CafeInfo>>) -> Unit) {
//        val call = apiService.getCafeData()
//
//        call.enqueue(object : Callback<ApiRespense<ArrayList<CafeInfo>>> {
//            override fun onResponse(call: Call<ApiRespense<ArrayList<CafeInfo>>>, response: Response<ApiRespense<ArrayList<CafeInfo>>>) {
//                if (response.isSuccessful) {
//                    val apiResponse = response.body() ?: return
//
//                    callback(apiResponse)
//                }
//            }
//            override fun onFailure(call: Call<ApiRespense<ArrayList<CafeInfo>>>, t: Throwable) {
//                // 통신 실패 처리
//                Log.d("errorrrrr", "${t}, ${call}")
//            }
//        })
//    }

//    fun getReservation(callback: (ApiRespense<ArrayList<CafeReservation>>) -> Unit) {
//        val call = apiService.getReservation()
//        Log.d("requManager getReservation", "successful!")
//
//        call.enqueue(object : Callback<ApiRespense<ArrayList<CafeReservation>>> {
//            override fun onResponse(call: Call<ApiRespense<ArrayList<CafeReservation>>>, response: Response<ApiRespense<ArrayList<CafeReservation>>>) {
//                Log.d("response is Successful_1", "successful!")
//                val apiResponse = response.body() ?: return
//                Log.d("response is Successful_2", "successful!")
//
//                callback(apiResponse)
//                Log.d("callback success", "successful!")
//
//
//            }
//            override fun onFailure(call: Call<ApiRespense<ArrayList<CafeReservation>>>, t: Throwable) {
//                // 통신 실패 처리
//                Log.d("errorrrrr", "${t}, $call")
//            }
//        })
//
//        Log.d("하하 안되노", "시시히히!")
//
//    }
}