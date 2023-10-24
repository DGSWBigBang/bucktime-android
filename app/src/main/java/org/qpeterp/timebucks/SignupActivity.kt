package org.qpeterp.timebucks

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.qpeterp.timebucks.databinding.ActivitySignupBinding
import org.qpeterp.timebucks.databinding.ActivityStartBinding

class SignupActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignupBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.idSigninButton.setOnClickListener {
            val intent = Intent(this, MainMainActivity::class.java)
            startActivity(intent)
        }
    }
}