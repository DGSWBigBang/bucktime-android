package org.qpeterp.timebucks.cafeInfoViewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.qpeterp.timebucks.databinding.ActivityOrderCafeBinding

class OrderCafeActivity: AppCompatActivity() {
    private val binding by lazy { ActivityOrderCafeBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
    }
}