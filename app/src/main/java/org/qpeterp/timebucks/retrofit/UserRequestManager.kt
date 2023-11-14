package org.qpeterp.timebucks.retrofit

import android.util.Log
import android.widget.Toast
import org.qpeterp.timebucks.dataClass.CafeTableResponse
import org.qpeterp.timebucks.JoinRequest
import org.qpeterp.timebucks.LoginRequest
import org.qpeterp.timebucks.LoginResponse
import org.qpeterp.timebucks.OrderShowUserResponseItem
import org.qpeterp.timebucks.ReservationRequest
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

    fun cafe(cafeIdx: Int, callback: (CafeTableResponse) -> Unit) {
        val cafeCall = apiService.showCafeDesk(cafeIdx)
        cafeCall.enqueue(object : Callback<CafeTableResponse> {
            override fun onResponse(
                call: Call<CafeTableResponse>,
                response: Response<CafeTableResponse>
            ) {
                val cafeResponse = response.body() ?: return
                Log.d("cafeTable loglog", "$cafeResponse")
                callback(cafeResponse)

            }

            override fun onFailure(call: Call<CafeTableResponse>, t: Throwable) {

                Log.d("Cafe Response Error", "Cafe Response is Error", t)
            }
        })
    }

    fun reservation(reservationRequest: ReservationRequest, token: String, callback: (StringResponse) -> Unit) {
        val reservationCall = apiService.reservationCreate(reservationRequest, "Bearer $token")
        reservationCall.enqueue(object : Callback<StringResponse> {
            override fun onResponse(
                call: Call<StringResponse>,
                response: Response<StringResponse>
            ) {
                Log.d("reservationResponse Error1", "Reservation Response is Error ${response.code()}")

                val reservationResponse = response.body() ?: return
                Log.d("reservationResponse Error2", "Reservation Response is Error")

                callback(reservationResponse)
            }

            override fun onFailure(call: Call<StringResponse>, t: Throwable) {
                Log.d("reservationResponse Error", "Reservation Response is Error", t)
            }
        })
    }

    fun orderShow(token: String, callback: (ArrayList<OrderShowUserResponseItem>) -> Unit) {
        val orderShowCall = apiService.orderShowUser("Bearer $token")
        orderShowCall.enqueue(object : Callback<ArrayList<OrderShowUserResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<OrderShowUserResponseItem>>,
                response: Response<ArrayList<OrderShowUserResponseItem>>
            ) {
                Log.d("cafeTable loglog", "$token")
                val OrderShowUserResponseItem = response.body() ?: return
                Log.d("cafeTable loglog", "$OrderShowUserResponseItem")
                Log.d("cafeTable loglog", "$token")

                callback(OrderShowUserResponseItem)
            }

            override fun onFailure(call: Call<ArrayList<OrderShowUserResponseItem>>, t: Throwable) {

                Log.d("ErrorFuck", "tlqkf", t)
            }
        })
    }
}