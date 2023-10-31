package org.qpeterp.timebucks.retrofit

import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.Toast
import okhttp3.OkHttpClient
import org.qpeterp.timebucks.CafeInfo
import org.qpeterp.timebucks.JoinRequest
import org.qpeterp.timebucks.LoginRequest
import org.qpeterp.timebucks.LoginResponse
import org.qpeterp.timebucks.MainMainActivity
import org.qpeterp.timebucks.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.math.log

class RequestManager {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.80.162.7:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)

    fun getCafeInfo(callback: (ApiRespense<ArrayList<CafeInfo>>) -> Unit) {
        val call = apiService.getCafeData()

        call.enqueue(object : Callback<ApiRespense<ArrayList<CafeInfo>>> {
            override fun onResponse(
                call: Call<ApiRespense<ArrayList<CafeInfo>>>,
                response: Response<ApiRespense<ArrayList<CafeInfo>>>
            ) {
                if (response.isSuccessful) {
                    // 날씨 데이터를 처리하는 코드를 작성

                    val apiResponse = response.body()
                    val data = apiResponse

                    callback(data!!)

                } else {
                    // 에러 처리
                    Log.d("resdataError", "${response.code()}")
                }
            }

            override fun onFailure(call: Call<ApiRespense<ArrayList<CafeInfo>>>, t: Throwable) {
                // 통신 실패 처리
                Log.d("errorrrrr", "${t}, ${call}")
            }
        })
    }

    fun login(loginData: LoginRequest, callback: (LoginResponse) -> Unit) {
        Log.d("Response Already Before", "yes")
        val loginCall = apiService.login(loginData)
        loginCall.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val loginResponse = response.body() ?: return
                Log.d("Response Success", "Login Response is Success")
                callback(loginResponse)
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("Response Error", "Login Response is Error", t)
            }
        })
    }

    fun register(joinRequest: JoinRequest, callback: (StringResponse) -> Unit) {
        val registerCall = apiService.create(joinRequest)
        registerCall.enqueue(object : Callback<StringResponse> {
            override fun onResponse(
                call: Call<StringResponse>,
                response: Response<StringResponse>
            ) {

                val joinResponse = response.body() ?: return
                Log.d("JoinResponse Success", response.body()!!.message)
                callback(joinResponse)
            }

            override fun onFailure(call: Call<StringResponse>, t: Throwable) {
                Log.d("JoinResponse Error", "response is error", t)
            }
        })
    }
}