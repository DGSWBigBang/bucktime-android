package org.qpeterp.timebucks.cafeInfoViewer

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
    }
}