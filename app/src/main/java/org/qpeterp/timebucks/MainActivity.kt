package org.qpeterp.timebucks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.qpeterp.timebucks.databinding.ActivityStartBinding
import android.util.Log
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val startToJoinIntent = Intent(this, JoinActivity::class.java)
        binding.idSigninButton.setOnClickListener{startActivity(startToJoinIntent)}

        val startToSignupIntent = Intent(this, SignupActivity::class.java)

        val joinToLoginIntent = Intent(this, JoinActivity::class.java)


        binding.startJoin.setOnClickListener{
            try {
                startActivity(startToSignupIntent)
            } catch (e: Exception) {
                Log.d("asdfasdf", "$e")
            }
        }
    }
}