package org.qpeterp.timebucks.mainFragments

import android.R
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.qpeterp.timebucks.CustomAdapter
import org.qpeterp.timebucks.databinding.FragmentTimeBinding
import org.qpeterp.timebucks.retrofit.RequestManager


class FragmentTime: Fragment() {
    private val binding by lazy { FragmentTimeBinding.inflate(layoutInflater) }
    private val requestManager = RequestManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding.showButton.setOnClickListener {
            requestManager.getReservation {
                binding.idDeskName.text = it.deskName
                binding.idStartTime.text = it.startTime
                binding.idFinishTime.text = it.finishTime
            }
        }
        return binding.root
    }
}