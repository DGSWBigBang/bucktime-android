package org.qpeterp.timebucks.mainFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.qpeterp.timebucks.databinding.FragmentTimeBinding
import org.qpeterp.timebucks.retrofit.RequestManager


class FragmentTime: Fragment() {
    private val binding by lazy { FragmentTimeBinding.inflate(layoutInflater) }
    private val requestManager = RequestManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        requestManager.getReservation {
        }

        return binding.root
    }
}