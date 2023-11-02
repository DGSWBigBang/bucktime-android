package org.qpeterp.timebucks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.qpeterp.timebucks.databinding.ActivitySignupBinding
import org.qpeterp.timebucks.retrofit.RequestManager
import org.qpeterp.timebucks.retrofit.UserRequestManager

class SignupActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySignupBinding.inflate(layoutInflater) }
    private val userRequestManager = UserRequestManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.orJoin.setOnClickListener {
            val loginToJoinIntent = Intent(this, JoinActivity::class.java)
            startActivity(loginToJoinIntent)
            finish()
        }

        binding.idSigninButton.setOnClickListener {
            val email = binding.textInputEditText.text.toString()
            val pw = binding.textInputEditText6.text.toString()
            val intent = Intent(this, MainMainActivity::class.java)
            var loginRequest = LoginRequest(pw, email)
            Log.d("Request Data", "$loginRequest")
            Log.d("Response before", "yes")
            userRequestManager.login(loginRequest) {
                Log.d("Response Result", "asdfsdfsdf")
                if(it != null) {
                     try {
                        val sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("accessToken", it.accessToken)
                        editor.putString("refreshToken", it.refreshToken)
                        startActivity(intent)
                    } catch (e:Exception) {
                        Log.d("pressed loginButton error", "$e")
                    }

                }
            }
        }
    }

}