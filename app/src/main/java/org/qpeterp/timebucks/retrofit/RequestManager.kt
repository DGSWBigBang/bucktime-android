package org.qpeterp.timebucks.retrofit

import android.util.Log
import android.widget.Toast
import org.qpeterp.timebucks.cafeInfoViewer.OrderCafeActivity
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

object RequestManager {

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

    fun getReservation(token: String, callback: (CafeReservation) -> Unit) {
        val call = apiService.getReservation("Bearer $token")
        Log.d("requManager getReservation", "successful!")

        call.enqueue(object : Callback<CafeReservation> {
            override fun onResponse(call: Call<CafeReservation>, response: Response<CafeReservation>) {
                Log.d("response is Successful_1", "successful!")
                val apiResponse = response.body() ?: return
                Log.d("response is Successful_2", "successful!")

                callback(apiResponse)
                Log.d("callback success", "successful!")

            }
            override fun onFailure(call: Call<CafeReservation>, t: Throwable) {
                // 통신 실패 처리
                Log.e("errorrrrr", "$call", t)
            }
        })

        Log.d("하하 안되노", "시시히히!")

    }

    fun getUserData(token: String, callback: (UserInfo) -> Unit) {
        Log.d("requManager getReservationUser getUserData_1", "$token")

        val call = apiService.getUserData("Bearer $token")
        Log.d("requManager getReservationUser getUserData_2", "$token")


        call.enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                Log.d("apiResponse의 값이 null 임;;", "ㄹㅇㅋㅋ")
                val apiResponse = response.body() ?: return

                Log.d("callback successUser", "successful!")
                callback(apiResponse)

            }
            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                // 통신 실패 처리
                Log.d("errorrrrr getUserData", "${t.message}", t)
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
                Log.d("requestManager", "apiResponse의 값이 null 임;;")
                val apiResponse = response.body() ?: return

                Log.d("requestManger", "Success: $apiResponse")
                callback(apiResponse)

            }
            override fun onFailure(call: Call<MenuData>, t: Throwable) {
                // 통신 실패 처리
                Log.d("errorrrrr", "${t}, $call")
            }
        })
    }

    fun postOrder(menuIdx: Int, accessToken: String, callback: (String) -> Unit) {
        Log.d("requManager postOrder", "accessToken: $accessToken")
        if (accessToken == "토큰 없음") {
            return
        }
        val call = apiService.postOrder(menuIdx, "Bearer $accessToken")
        Log.d("requManager postOrder", "accessToken: $accessToken")

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("requestManager", "apiResponse의 값이 null 임;;")
                val apiResponse = response.body() ?: return

                Log.d("requestManger postOrder", "Success: $apiResponse")
                callback(apiResponse)

            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                // 통신 실패 처리
                Log.d("errorrrrr", "${t}, $call")
            }
        })
    }
}