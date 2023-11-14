package org.qpeterp.timebucks.mainFragments

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
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
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.qpeterp.timebucks.cafeInfoViewer.PopupActivity
import org.qpeterp.timebucks.R
import org.qpeterp.timebucks.cafeInfoViewer.OrderCafeActivity
import org.qpeterp.timebucks.databinding.FragmentMapBinding
import org.qpeterp.timebucks.retrofit.RequestManager


class FragmentMap : Fragment(),OnMapReadyCallback, OnMarkerClickListener {
    private val binding by lazy { FragmentMapBinding.inflate(layoutInflater) }
    private lateinit var googleMap: GoogleMap
    private var isFirstLocationUpdate = true
    private var marker: Marker? = null
    private var tOrF = -1
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
        Log.d("onCreateViewasdf", "asdfasdfasdf")

        return rootView
    }

    private fun startLocationPermissionRequest() {
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }

    //지도 객체를 사용할 수 있을 때 자동으로 호출되는 함수
    override fun onMapReady(map: GoogleMap) {
        this.googleMap = map
    }

    private fun setMarkers(latitude: Double, longitude: Double) {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.marker_self_position)

        val selfMarkerOptions = MarkerOptions()
            .position(LatLng(latitude, longitude))
            .title("내 위치")
            .icon(BitmapDescriptorFactory.fromBitmap(bitmap))

        val target = LatLng(latitude, longitude)

        val cameraPosition = CameraPosition.Builder()
            .target(target) // 이동할 위치
            .zoom(12f) // 줌 레벨 설정
            .build()

            marker = googleMap.addMarker(selfMarkerOptions)
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            googleMap.addMarker(selfMarkerOptions)?.showInfoWindow()
            Log.d("setMArkers 실행", "제발좀")
    }

    private fun toStartMapCafe() {
        RequestManager.getCafeInfo {
            for (i in 0 until it.size) {
                setMarkersCafe(it[i].cafeName, it[i].latitude.toDouble(), it[i].longitude.toDouble())
            }
        }
    }

    private fun setMarkersCafe(title: String, latitude: Double, longitude: Double) {
        val markerOptions = MarkerOptions()
            .position(LatLng(latitude, longitude))
            .title("$title")
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))

        googleMap.addMarker(markerOptions)?.showInfoWindow()
        googleMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val title = marker.title
        Log.d("titleMarker", "확인 로그")
        if (title != null) {
            // 여기서 title을 기반으로 어떤 카페를 클릭했는지 확인하고 해당 동작을 수행
            when (title) {
                "엄카페" -> {
                    Log.d("titleMarker", "확인된 title: $title")
                    tOrF = 0
                    onSetViewCafeData()
                }
                "이슬카페" -> {
                    // 카페2를 클릭한 경우 실행할 동작
                    tOrF = 1
                    onSetViewCafeData()
                }

            }

        }
        return true
    }


    // 마커를 삭제하는 함수
    private fun removeMarker() {
        marker?.remove() // 마커가 null이 아닌 경우에만 삭제
    }

    private fun checkPermission() {
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
                    Log.d("findLocation override", "asdfasdf")

                    val longitude = location.longitude
                    val latitude = location.latitude
                    Log.d("LocationListener", "위도경도: $longitude $latitude")
                    if (isFirstLocationUpdate) {
                        Log.d("asdfasdfasdf","asdfasdfasdf")
                        isFirstLocationUpdate = false
                        setMarkers(latitude, longitude)
                        toStartMapCafe()
                        Log.d("asdfasdfasdf","asdfasdfasdf")

                    }


                    // 딜레이를 주기 위해 Handler를 사용
                    Handler().postDelayed({
                        removeMarker()
                        setMarkers(latitude, longitude)
                    }, 600000) // 대충 오래뒤에 호출


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

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener)
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

    private fun onSetViewCafeData() {
        Log.d("onSetViewCafeData", "함수가 호출됨")
        //데이터 담아서 팝업(액티비티) 호출
        try {
            val intent = Intent(activity, PopupActivity::class.java)
            intent.putExtra("tOrF", tOrF) // tOrF 변수를 인텐트에 추가

            startActivity(intent)
        } catch (e:Exception) {
            Log.d("onSetViewCafeDataBug", "$e")
        }

    }

}
