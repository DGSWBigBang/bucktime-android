package org.qpeterp.timebucks.mainFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.qpeterp.timebucks.databinding.FragmentCafeBinding
import org.qpeterp.timebucks.retrofit.UserRequestManager

class FragmentCafe : Fragment() {
    private val binding by lazy { FragmentCafeBinding.inflate(layoutInflater) }
    private val userRequestManager = UserRequestManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.showOrderP.setOnClickListener {
            userRequestManager.orderShow(requireContext().getSharedPreferences("token", AppCompatActivity.MODE_PRIVATE).getString("accessToken", "토큰 없음").toString()) {

                Log.d("FragmentCafe", "$it")
                binding.idOrderShow.text = "${it[2].menuName}를 주문하셨습니다."
            }
        }

        return binding.root
    }
}
