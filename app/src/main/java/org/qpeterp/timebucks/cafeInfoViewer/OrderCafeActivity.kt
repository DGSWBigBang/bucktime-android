package org.qpeterp.timebucks.cafeInfoViewer

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.qpeterp.timebucks.CustomAdapter
import org.qpeterp.timebucks.databinding.ActivityOrderCafeBinding
import org.qpeterp.timebucks.retrofit.RequestManager

class OrderCafeActivity: AppCompatActivity() {
    private val binding by lazy { ActivityOrderCafeBinding.inflate(layoutInflater) }
    private val requestManager = RequestManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tOrF = intent.getIntExtra("tOrF", -1)


        Log.d("onCreateOrderCafeActivity", "실행")

        try {
            val testDataSet = ArrayList<ArrayList<String>>()

            if (tOrF != null) {
                Log.d("orderCafeActivity", "tOrF가 null이 아닙니다.")
                Log.d("orderCafeActivity", "${tOrF+1}")

                requestManager.getMenuListData(tOrF + 1) { menuList ->
                    val recyclerView: RecyclerView = binding.recyclerViewTime
                    val linearLayoutManager = LinearLayoutManager(this as Context)
                    recyclerView.layoutManager = linearLayoutManager // LayoutManager 설정

                    Log.d("getMenuListData", "$menuList")
                    Log.d("getMenuListData", "사이즈: ${menuList.size}")

                    for (i in 1..menuList.size) {
                        val innerList = ArrayList<String>()

                        requestManager.getMenuData(i) { menu ->

                            innerList.add(menu.menuName)
                            innerList.add(menu.menuDescription)
                            innerList.add(menu.menuPrice.toString())

                            testDataSet.add(innerList)

                            Log.d("getMenuData testDataSet", "$testDataSet")


                            if (i == menuList.size) {
                                Log.d("getMenuData testDataSet", "$testDataSet")

                                // 마지막 메뉴 데이터를 처리한 경우 리사이클러뷰 초기화 및 어댑터 설정
                                val customAdapter = CustomAdapter(testDataSet)
                                recyclerView.adapter = customAdapter // 어댑터 설정
                                Log.d("orderCafeActivity", "for문이 끝났습니다.")
                            }
                        }
                        Log.d("orderCafeActivity", "for문이 계속 돌아가는 중입니다.")
                    }
                }
                Log.d("orderCafeActivity", "requestManager가 끝났습니다.")
            }
        } catch (e: Exception) {
            Log.d("주문", "$e")
        }


        setContentView(binding.root)
    }
}