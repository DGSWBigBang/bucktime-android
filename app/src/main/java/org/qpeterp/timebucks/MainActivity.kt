package org.qpeterp.timebucks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.qpeterp.timebucks.databinding.ActivityStartBinding
import org.qpeterp.timebucks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    private val serverCommu = ServerCommu()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



//        Log.d("onCreate", "complete")
//        val cafeInformation = serverCommu.LoadCafe(this) { arr ->
//            binding.data.text = "${arr.count()}"
//        }

    }
}
