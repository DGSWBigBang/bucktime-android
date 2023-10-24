package org.qpeterp.timebucks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.qpeterp.timebucks.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setContentView(R.layout.activity_start)

        val intent = Intent(this, JoinActivity::class.java)
        binding.idSigninButton.setOnClickListener{startActivity(intent)}
    }
}
