package org.qpeterp.timebucks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.qpeterp.timebucks.databinding.ActivityStartBinding

class JoinActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setContentView(R.layout.activity_join)
    }
}