package org.qpeterp.timebucks

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.qpeterp.timebucks.databinding.ActivityMainBinding
import org.qpeterp.timebucks.mainFragments.FragmentCafe
import org.qpeterp.timebucks.mainFragments.FragmentMap
import org.qpeterp.timebucks.mainFragments.FragmentMyPage
import org.qpeterp.timebucks.mainFragments.FragmentTime
import org.qpeterp.timebucks.retrofit.RequestManager

class MainMainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val requestManager = RequestManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFragmentAndBar()

        requestManager.getCafeInfo {
            if (it.success == false) {
                Toast.makeText(this, it.error, Toast.LENGTH_SHORT).show()
                Log.d("asdfasdf", it.error)
            }
            Toast.makeText(this, "${it.response}", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setFragmentAndBar() {
        val mapFragment = FragmentMap()
        val cafeFragment = FragmentCafe()
        val timeFragment = FragmentTime()
        val myPageFragment = FragmentMyPage()

        setCurrentFragment(mapFragment)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.map_menu -> {
                    setCurrentFragment(mapFragment)
                    Log.d("pressedMap","asdfasdf")
                }
                R.id.time_menu -> setCurrentFragment(timeFragment)
                R.id.cafe_menu -> setCurrentFragment(cafeFragment)
                R.id.myPage_menu -> setCurrentFragment(myPageFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(pFragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(binding.flFragment.id, pFragment).commit()
    }
}
