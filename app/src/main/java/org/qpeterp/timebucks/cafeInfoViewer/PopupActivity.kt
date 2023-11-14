package org.qpeterp.timebucks.cafeInfoViewer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import org.qpeterp.timebucks.databinding.ItemCafeInfoBinding
import org.qpeterp.timebucks.mainFragments.FragmentMap
import org.qpeterp.timebucks.retrofit.RequestManager


class PopupActivity : Activity() {
    private val binding by lazy { ItemCafeInfoBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tOrF = intent.getIntExtra("tOrF", -1)


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(binding.root)

        Log.d("onCreate PopupActivity", "start")

        //UI 객체생성

        RequestManager.getCafeInfo {
            Log.d("정보 넣기", "들어옴")
            Log.d("정보의 index", "${tOrF}")
            Log.d("정보", "$it")
            with(binding) {
                idCafeName.text = it[tOrF].cafeName
                idCafeAdress.text = it[tOrF].address
                idCafeInformation.text = it[tOrF].cafeDescription
                idCafePhoneNumber.text = "카폐 번호: ${it[tOrF].callNumber}"
                idCafeOpenTime.text = stringSlicing(0, it[tOrF].openTime)
                idCafeCloseTime.text = stringSlicing(1, it[tOrF].closeTime)
            }
        }

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(this.window.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.gravity = Gravity.BOTTOM // 위치 설정

        this.window.attributes = layoutParams;


        binding.idCafeTimeButton.setOnClickListener {
            Log.d("버튼 눌림", "start")
            mOnClose(tOrF)
        }
    }

    //확인 버튼 클릭
    private fun mOnClose(tOrF: Int) {
        try {
            Log.d("mOnClose start_1", "success")
            val intent = Intent(this, ResponseCafeActivity::class.java)
            intent.putExtra("tOrF", tOrF) // tOrF 변수를 인텐트에 추가

            startActivity(intent)
            Log.d("mOnClose start_2", "success")
        } catch (e:Exception) {
            Log.d("mOnCloseBug", "$e")
        }
    }

    private fun stringSlicing(opORcl: Int, time: String): String {
        val hour = time.slice(0..1)
        val min = time.slice(2..3)

        if (opORcl == 0) {
            return "open: $hour:$min"
        }
        else {
            return "close: $hour:$min"
        }
    }
}