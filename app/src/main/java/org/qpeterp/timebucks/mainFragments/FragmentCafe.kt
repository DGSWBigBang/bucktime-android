package org.qpeterp.timebucks.mainFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.qpeterp.timebucks.databinding.FragmentCafeBinding
import org.qpeterp.timebucks.databinding.FragmentMapBinding

class FragmentCafe: Fragment() {
    private val binding by lazy { FragmentCafeBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using the binding object
        return binding.root
    }

}