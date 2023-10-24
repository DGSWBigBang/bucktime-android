package org.qpeterp.timebucks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.qpeterp.timebucks.databinding.ActivityStartBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}
