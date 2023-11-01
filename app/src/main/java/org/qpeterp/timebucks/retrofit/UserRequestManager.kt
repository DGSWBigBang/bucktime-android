package org.qpeterp.timebucks.retrofit

import android.util.Log
import org.qpeterp.timebucks.JoinRequest
import org.qpeterp.timebucks.LoginRequest
import org.qpeterp.timebucks.LoginResponse
import org.qpeterp.timebucks.StringResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserRequestManager {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://timebucks.kro.kr")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService: ApiService = retrofit.create(ApiService::class.java)
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
                Log.d("JoinResponse Error", "${t.message}", t)
            }
        })
    }
}