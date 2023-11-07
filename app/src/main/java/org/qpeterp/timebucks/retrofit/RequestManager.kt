package org.qpeterp.timebucks.retrofit

import android.util.Log
import org.qpeterp.timebucks.dataClass.MenuData
import org.qpeterp.timebucks.dataClass.CafeInfo
import org.qpeterp.timebucks.dataClass.CafeReservation
import org.qpeterp.timebucks.dataClass.MenuListDataItem
import org.qpeterp.timebucks.dataClass.UserInfo
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
                    Log.d("getCafeInfo is Sucessful!_2", "$apiResponse")

                    callback(apiResponse)
                } else {
                    // 에러 처리
                    Log.d("resdataError", "${response.code()}")
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

    fun getUserData(callback: (UserInfo) -> Unit) {
        val call = apiService.getUserData()
        Log.d("requManager getReservationUser", "successful!")

        call.enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                Log.d("apiResponse의 값이 null 임;;", "ㄹㅇㅋㅋ")
                val apiResponse = response.body() ?: return

                Log.d("callback successUser", "successful!")
                callback(apiResponse)

            }
            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                // 통신 실패 처리
                Log.d("errorrrrr", "${t}, $call")
            }
        })

        Log.d("하하 안되노", "시시히히!")

    }

    fun getMenuListData(cafeIdx: Int, callback: (ArrayList<MenuListDataItem>) -> Unit) {

        val call = apiService.getMenuListData(cafeIdx)
        Log.d("requManager getMenuListData", "successful!")

        call.enqueue(object : Callback<ArrayList<MenuListDataItem>> {
            override fun onResponse(call: Call<ArrayList<MenuListDataItem>>, response: Response<ArrayList<MenuListDataItem>>) {
                Log.d("apiResponse의 값이 null 임;; getMenuListData", "ㄹㅇㅋㅋ")
                val apiResponse = response.body() ?: return

                Log.d("callback getMenuListData", "$apiResponse")
                callback(apiResponse)

            }
            override fun onFailure(call: Call<ArrayList<MenuListDataItem>>, t: Throwable) {
                // 통신 실패 처리
                Log.d("getMenuListData errorrrrr", "${t}, $call")
            }
        })

    }

    fun getMenuData(menuIdx: Int, callback: (MenuData) -> Unit) {
        val call = apiService.getMenuData(menuIdx)
        Log.d("requManager getReservationUser", "successful!")

        call.enqueue(object : Callback<MenuData> {
            override fun onResponse(call: Call<MenuData>, response: Response<MenuData>) {
                Log.d("apiResponse의 값이 null 임;;", "ㄹㅇㅋㅋ")
                val apiResponse = response.body() ?: return

                Log.d("callback successUser", "$apiResponse")
                callback(apiResponse)

            }
            override fun onFailure(call: Call<MenuData>, t: Throwable) {
                // 통신 실패 처리
                Log.d("errorrrrr", "${t}, $call")
            }
        })

    }
}