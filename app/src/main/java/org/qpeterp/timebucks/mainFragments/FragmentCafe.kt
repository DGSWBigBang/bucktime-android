package org.qpeterp.timebucks.mainFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.qpeterp.timebucks.CustomAdapter
import org.qpeterp.timebucks.databinding.FragmentCafeBinding
import org.qpeterp.timebucks.retrofit.RequestManager

class FragmentCafe : Fragment() {
    private val binding by lazy { FragmentCafeBinding.inflate(layoutInflater) }
    private val requestManager = RequestManager()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using the binding object

        try {
            recyclerView = binding.recyclerView
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = adapter

            requestManager.getReservation { apiResponse ->
                Log.d("asdfasdf", "$apiResponse")

                val data = apiResponse[0]
                val dataSet = arrayOf(data.rezIdx,
                    data.deskName, data.startTime, data.finishTime,
                    data.userMail, data.deskIdx, data.used)

                Log.d("asdfasdf", "$dataSet")

                // 요청 실패 또는 데이터 없음
                Toast.makeText(activity, "데이터를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show()

            }
        } catch (e: Exception) {
            Log.d("asdfasdfasdf", "$e")
        }


        return binding.root
    }
}
