package org.qpeterp.timebucks.mainFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.qpeterp.timebucks.databinding.FragmentMapBinding

class FragmentMap : Fragment(), OnMapReadyCallback {

    private val binding by lazy { FragmentMapBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using the binding object
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the MapView
        binding.idGoogleMaps.onCreate(savedInstanceState)

        // Get the MapView and load the map
        binding.idGoogleMaps.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Set up the GoogleMap object
        val myLocation = LatLng(37.7749, -122.4194) // San Francisco coordinates
        googleMap.addMarker(MarkerOptions().position(myLocation).title("San Francisco"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation))
    }
}

