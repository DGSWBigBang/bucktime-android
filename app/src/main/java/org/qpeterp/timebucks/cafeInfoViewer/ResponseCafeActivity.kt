package org.qpeterp.timebucks.cafeInfoViewer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.qpeterp.timebucks.ReservationRequest
import org.qpeterp.timebucks.StringResponse
import org.qpeterp.timebucks.databinding.ActivityResponseCafeBinding
import org.qpeterp.timebucks.retrofit.UserRequestManager

class ResponseCafeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityResponseCafeBinding.inflate(layoutInflater) }
    private val userRequestManager = UserRequestManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val tOrF = intent.getIntExtra("tOrF", -1)

        userRequestManager.cafe(tOrF+1) {
            binding.tableMaxNum.text = "테이블의 최대 번호는 ${it.size}번 입니다."
        }

        binding.confirmButton.setOnClickListener {
            val useTableTimeString = binding.useTableTime.text.toString().trim()
            val useTableTime = useTableTimeString.toInt()
            binding.confirmText.text = "가격은 ${4000*useTableTime}원입니다."
        }

        binding.responseButton.setOnClickListener {
            userRequestManager.cafe(tOrF+1) {
                val useTableTimeString = binding.useTableTime.text.toString().trim()
                val useTableTime = useTableTimeString.toInt()
                val useTableIdxString = binding.idResponseTable.text.toString().trim()
                val useTableIdx = useTableIdxString.toInt()
                if (useTableTime == 0) {
                    Toast.makeText(this, "테이블 이용 시간이 0입니다.", Toast.LENGTH_SHORT).show()
                } else if (useTableIdx > it.size) {
                    Log.d("userRequestManager Cafe It :", "${it.size}")
                    Toast.makeText(this, "테이블 번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    val reservationRequest = ReservationRequest(useTableIdx, useTableTime)
                    Log.d("짜증나는 에러 1", "화가난다")
                    userRequestManager.reservation(reservationRequest, getSharedPreferences("token", MODE_PRIVATE).getString("accessToken", "토큰 없음").toString()) {
                        Log.d("짜증나는 에러 2", "화가난다2")
                        if (it.message == "예약 완료") {
                            val nextActivity = Intent(this, OrderCafeActivity::class.java)
                            nextActivity.putExtra("tOrF", tOrF) // tOrF 변수를 인텐트에 추가
                            startActivity(nextActivity)

                        }
                }
            }
        }
    }
}}