package org.qpeterp.timebucks

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.qpeterp.timebucks.databinding.ActivityJoinBinding
import org.qpeterp.timebucks.databinding.ActivityStartBinding
import org.qpeterp.timebucks.retrofit.RequestManager
import org.qpeterp.timebucks.retrofit.UserRequestManager
import kotlin.math.log

class JoinActivity : AppCompatActivity() {
    private val binding by lazy { ActivityJoinBinding.inflate(layoutInflater) }
    private val userRequestManager = UserRequestManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.idJoinButton.setOnClickListener {
            val phoneNumber = binding.textInputEditText5.text.toString()
            val email = binding.textInputEditText.text.toString()
            val username = binding.textInputEditText4.text.toString()
            val password = binding.textInputEditText2.text.toString()

            if (email.isBlank()) {
                Toast.makeText(this, "이메일이 비어있습니다.", Toast.LENGTH_SHORT).show()
            } else if (password.isBlank()) {
                Toast.makeText(this, "비밀번호가 비어있습니다.", Toast.LENGTH_SHORT).show()
            } else if (username.isBlank()) {
                Toast.makeText(this, "이름이 비어있습니다.", Toast.LENGTH_SHORT).show()
            } else if (phoneNumber.isBlank()) {
                Toast.makeText(this, "전화번호가 비어있습니다.", Toast.LENGTH_SHORT).show()
            }

            val joinRequest = JoinRequest(phoneNumber, email, username, password)
            userRequestManager.register(joinRequest) {
                if (it.message == "회원가입 완료") {
                    val joinIntent = Intent(this, SignupActivity::class.java)
                    startActivity(joinIntent)
                }
            }
        }
    }
}