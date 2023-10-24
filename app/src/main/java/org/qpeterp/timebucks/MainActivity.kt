package org.qpeterp.timebucks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.qpeterp.timebucks.databinding.ActivityStartBinding
import org.qpeterp.timebucks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.idSigninButton.setOnClickListener {
            try {
                val intent = Intent(this, MainMainActivity::class.java)
                startActivity(intent)
                Log.d("fdafda","complete")
            } catch (e: Exception) {
                Log.d("asdfasdf", "${e}")
            }

        }

    }
}
