package org.qpeterp.timebucks.mainFragments

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.qpeterp.timebucks.R
import org.qpeterp.timebucks.databinding.FragmentMapBinding
import org.qpeterp.timebucks.retrofit.RequestManager

class FragmentMap : Fragment(),OnMapReadyCallback {
    private val binding by lazy { FragmentMapBinding.inflate(layoutInflater) }
    private lateinit var mGoogleMap: GoogleMap
    private var isFirstLocationUpdate = true
    private var marker: Marker? = null
    private val requestManager = RequestManager()
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            findLocation()
            Log.d("permission ok", "ok")
        } else {
            showDialog()
            Log.d("permission ok", "shit")

        }
    }
    private lateinit var mapView : MapView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        isFirstLocationUpdate = true
        val rootView = inflater.inflate(R.layout.fragment_map,container,false)
        mapView = rootView.findViewById(binding.idGoogleMaps.id) as MapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        checkPermission()
        toStartMapCafe()

        Log.d("onCreateViewasdf", "asdfasdfasdf")

        return rootView
    }

    private fun startLocationPermissionRequest() {
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    //지도 객체를 사용할 수 있을 때 자동으로 호출되는 함수
    override fun onMapReady(map: GoogleMap) {
        mGoogleMap = map
    }

    private fun setMarkers(latitude: Double, longitude: Double) {
        try {
            val selfMarkerOptions = MarkerOptions()
                .position(LatLng(latitude, longitude))
                .title("내 위치")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))

            val target = LatLng(latitude, longitude)

            val cameraPosition = CameraPosition.Builder()
                .target(target) // 이동할 위치
                .zoom(12f) // 줌 레벨 설정
                .build()

            try {
                marker = mGoogleMap.addMarker(selfMarkerOptions)
                mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                mGoogleMap.addMarker(selfMarkerOptions)?.showInfoWindow()
                Log.d("setMArkers 실행", "제발좀")
            } catch (e:Exception) {
                Log.d("bugbugbug", "$e")
            }
        } catch (e:Exception) {
            Log.d("setMarkersOk?", "$e")
        }
    }

    private fun toStartMapCafe() {
        requestManager.getCafeInfo {
            for (i in 0 until it.size) {
                setMarkersCafe(it[i].cafeName, it[i].latitude.toDouble(), it[i].longitude.toDouble())
            }
        }
    }

    private fun setMarkersCafe(title: String, latitude: Double, longitude: Double) {
        try {
            val markerOptions = MarkerOptions()
                .position(LatLng(latitude, longitude))
                .title("$title")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))

            try {
                mGoogleMap.addMarker(markerOptions)?.showInfoWindow()
                Log.d("setMArkersCafe 실행", "제발좀")
            } catch (e:Exception) {
                Log.d("bugbugbug", "$e")
            }
        } catch (e:Exception) {
            Log.d("setMarkersOk?", "$e")
        }
    }

    // 마커를 삭제하는 함수
    private fun removeMarker() {
        marker?.remove() // 마커가 null이 아닌 경우에만 삭제
    }


    private fun checkPermission() {
        try {
            // 권한 체크해서 권한이 있을 때
            if(ContextCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                Log.d("asdfasdf", "have permission")
                findLocation()
            }
            // 권한이 없을 때 권한을 요구함
            else {
                startLocationPermissionRequest()
            }
        } catch (e:Exception) {
            Log.d("checkPermission", "$e")
        }

    }

    private fun showDialog() {
        val msgBuilder = AlertDialog.Builder(activity)
            .setTitle("권한 거부")
            .setMessage("권한 거부시 앱을 이용하실 수 없습니다.")
            .setPositiveButton(
                "권한 요청"
            ) { _, _ -> startLocationPermissionRequest()}
        val msgDlg = msgBuilder.create()
        msgDlg.show()
    }

    private fun findLocation() {
        try {
            // 위치 정보를 가져오는데 사용할 LocationListener를 초기화
            val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            Log.d("findLocation first_3", "asdfasdf")

            val locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    try {
                        Log.d("findLocation override", "asdfasdf")

                        val longitude = location.longitude
                        val latitude = location.latitude
                        Log.d("LocationListener", "위도경도: $longitude $latitude")
                        if (isFirstLocationUpdate) {
                            isFirstLocationUpdate = false
                            setMarkers(latitude, longitude)
                        }

                        // 딜레이를 주기 위해 Handler를 사용
                        Handler().postDelayed({
                            removeMarker()
                            setMarkers(latitude, longitude)
                        }, 60000) // 1분에 한 번씩 호출
                    } catch (e:Exception) {
                        Log.d("asdfasdfasdfasdf", "$e")
                    }


                    // 위치 정보를 가져온 후 필요한 작업을 수행
                }
                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                    // 위치 공급자 상태 변경 시 처리
                }
                override fun onProviderEnabled(provider: String) {
                    // 위치 공급자 활성화 시 처리
                }
                override fun onProviderDisabled(provider: String) {
                    // 위치 공급자 비활성화 시 처리
                }
            }
            // 위치 정보 업데이트를 요청
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener)

        } catch (e:Exception) {
            Log.d("findLocationOk", "$e")
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}
