package org.qpeterp.timebucks.mainFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.qpeterp.timebucks.adapter.CustomAdapter
import org.qpeterp.timebucks.databinding.FragmentCafeBinding
import org.qpeterp.timebucks.retrofit.RequestManager
import org.qpeterp.timebucks.retrofit.UserRequestManager

class FragmentCafe : Fragment() {
    private val binding by lazy { FragmentCafeBinding.inflate(layoutInflater) }
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter
    private val userRequestManager = UserRequestManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using the binding object

        try {
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = adapter

            RequestManager.getReservation { data ->
                Log.d("asdfasdf", "$data")

                val dataSet = arrayOf(
                    data.rezIdx,
                    data.deskName, data.startTime, data.finishTime,
                    data.userMail, data.deskIdx, data.used
                )

                Log.d("asdfasdf", "$dataSet")

                // 요청 실패 또는 데이터 없음
                Toast.makeText(activity, "데이터를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()
                binding.showOrderP.setOnClickListener {
                    userRequestManager.orderShow(
                        requireContext().getSharedPreferences(
                            "token",
                            AppCompatActivity.MODE_PRIVATE
                        ).getString("accessToken", "토큰 없음").toString()
                    ) {

                        Log.d("FragmentCafe", "$it")
                        binding.idOrderShow.text = "${it[2].menuName}를 주문하셨습니다."
                    }
                }
            }
        } catch (e:Exception) {
            Log.e("asdfasdfasf", "",e)
        }

        return binding.root
    }
}
