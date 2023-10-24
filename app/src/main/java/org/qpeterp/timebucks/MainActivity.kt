package org.qpeterp.timebucks

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import org.qpeterp.timebucks.databinding.ActivityStartBinding
import org.qpeterp.timebucks.databinding.ActivityMainBinding
import java.lang.Exception
import java.util.jar.JarOutputStream

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityStartBinding.inflate(layoutInflater) }
    private val serverCommu = ServerCommu()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val startToJoinIntent = Intent(this, JoinActivity::class.java)
        binding.idSigninButton.setOnClickListener{startActivity(startToJoinIntent)}

        val startToSignupIntent = Intent(this, SignupActivity::class.java)

        binding.startJoin.setOnClickListener{
            try {
                startActivity(startToSignupIntent)
            } catch (e: Exception) {
                Log.d("asdfasdf", "$e")
            }
        }
    }
}