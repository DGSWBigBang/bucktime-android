package org.qpeterp.timebucks.mainFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.qpeterp.timebucks.databinding.FragmentTimeBinding
import org.qpeterp.timebucks.retrofit.RequestManager


class FragmentTime: Fragment() {
    private val binding by lazy { FragmentTimeBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        RequestManager.getReservation(requireContext().getSharedPreferences("token", AppCompatActivity.MODE_PRIVATE).getString("accessToken", "토큰 없음").toString()) {
            Log.d("FragmentTime", "getReservation's it:${it}")
            binding.idDeskName.text = it.deskName
            binding.idStartTime.text = it.startTime
            binding.idFinishTime.text = it.finishTime
        }
        return binding.root
    }
}