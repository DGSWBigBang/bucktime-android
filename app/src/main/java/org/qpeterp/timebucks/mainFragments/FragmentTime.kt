package org.qpeterp.timebucks.mainFragments

import android.os.Bundle
import android.util.Log
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
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using the binding object

        requestManager.getCafeInfo {
            binding.idText.text = it.toString()
            Log.d("CafeInFoYes", "$it")
        }

        return binding.root
    }
}