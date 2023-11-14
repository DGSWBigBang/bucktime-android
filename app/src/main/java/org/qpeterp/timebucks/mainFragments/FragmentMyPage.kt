package org.qpeterp.timebucks.mainFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.qpeterp.timebucks.databinding.FragmentMypageBinding
import org.qpeterp.timebucks.retrofit.RequestManager

class FragmentMyPage: Fragment() {
    private val binding by lazy { FragmentMypageBinding.inflate(layoutInflater) }
    private val requestManager = RequestManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using the binding object

        Log.d("setUserName", "first")

        requestManager.getUserData(requireContext().getSharedPreferences("token", AppCompatActivity.MODE_PRIVATE).getString("accessToken", "토큰 없음").toString()) {
            binding.idUserName.text = it.userName
            Log.d("setUserName", "$it")
        }

        Log.d("setUserName", "second")

        return binding.root
    }

}