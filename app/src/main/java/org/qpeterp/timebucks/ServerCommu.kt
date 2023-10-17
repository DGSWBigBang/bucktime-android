package org.qpeterp.timebucks

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class ServerCommu{
    private var queue: RequestQueue? = null
    private var cafeInfoList: ArrayList<CafeInfo> = ArrayList()
    fun LoadCafe(thiss: Activity, callback: (ArrayList<CafeInfo>) -> Unit) {
        queue = Volley.newRequestQueue(thiss)

        var url = "http://timebucks.kro.kr/cafe/show"
        var cafeInfo: CafeInfo? = null

        val stringRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {res ->
                for(i in 0 until res.length())
                {
                    val response = res[i] as JSONObject
                    cafeInfo = CafeInfo(response.getString("address"),
                        response.getString("cafeDescription"),
                        response.getInt("cafeIdx"),
                        response.getString("cafeName"),
                        response.getString("ownerMail"))

                    cafeInfoList.add(cafeInfo!!)
                }
                callback(cafeInfoList)
            },
            { error ->
                Log.d("kkang", "error......$error")
            }
        )
        queue?.add(stringRequest)
    }
}