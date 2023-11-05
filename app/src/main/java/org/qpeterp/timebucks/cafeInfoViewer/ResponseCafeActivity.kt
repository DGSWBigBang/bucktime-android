package org.qpeterp.timebucks.cafeInfoViewer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.qpeterp.timebucks.databinding.ActivityOrderCafeBinding
import org.qpeterp.timebucks.databinding.ActivityResponseCafeBinding

class ResponseCafeActivity: AppCompatActivity() {
    private val binding by lazy { ActivityResponseCafeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainCafeFragmentStart","yesss")

        setContentView(binding.root)

        val tOrF = intent.getIntExtra("tOrF", -1)


        try {
            binding.responseButton.setOnClickListener {
                val nextActivity = Intent(this, OrderCafeActivity::class.java)
                nextActivity.putExtra("tOrF", tOrF) // tOrF 변수를 인텐트에 추가
                startActivity(nextActivity)
            }
        } catch (e:Exception) {
            Log.d("responseCafeActivityBug", "$e")
        }

    }
}