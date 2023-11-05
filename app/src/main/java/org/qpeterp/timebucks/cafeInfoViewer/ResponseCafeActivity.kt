package org.qpeterp.timebucks.cafeInfoViewer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.Marker
import org.qpeterp.timebucks.databinding.ActivityResponseCafeBinding
import org.qpeterp.timebucks.mainFragments.FragmentMap
import org.qpeterp.timebucks.retrofit.UserRequestManager

class ResponseCafeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityResponseCafeBinding.inflate(layoutInflater) }
    private val userRequestManager = UserRequestManager()
    private val fragmentMap  = FragmentMap()
    private val intent = getIntent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.responseButton.setOnClickListener {
            val useTableTime = binding.useTableTime.text.toString()
            val useTableIdx = binding.idResponseTable.text.toString()
            if (useTableTime.isBlank()) {
                Toast.makeText(this, "테이블 이용 시간이 비어있습니다.", Toast.LENGTH_SHORT).show()
            } else if (useTableIdx.isBlank()) {
                Toast.makeText(this, "테이블 번호가 비어있습니다.", Toast.LENGTH_SHORT).show()
            }
        }

            userRequestManager.cafe(intent.getIntExtra("tOrF", 0)) {
                try {
                    binding.responseButton.setOnClickListener {
                        val nextActivity = Intent(this, OrderCafeActivity::class.java)
                        startActivity(nextActivity)
                    }
                } catch (e: Exception) {
                    Log.d("responseCafeActivityBug", "$e")
                }
            }
    }
}