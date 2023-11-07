package org.qpeterp.timebucks.cafeInfoViewer

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.qpeterp.timebucks.adapter.CustomAdapter
import org.qpeterp.timebucks.databinding.ActivityOrderCafeBinding
import org.qpeterp.timebucks.mainFragments.FragmentTime
import org.qpeterp.timebucks.retrofit.RequestManager
import java.text.DecimalFormat


class OrderCafeActivity : AppCompatActivity() {
    val binding by lazy { ActivityOrderCafeBinding.inflate(layoutInflater) }
    private val requestManager = RequestManager()
    private var menuOrNull: String? = null
    private var menuIdx: String? = null
    private var menuPrizes: Int? = null
    private val testDataSet = ArrayList<ArrayList<String>>()
    private val formatter: DecimalFormat = DecimalFormat("###,###")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("orderCafeOnCreate", "called onCreate")
        setContentView(binding.root)
        val tOrF = intent.getIntExtra("tOrF", -1)

        Log.d("onCreateOrderCafeActivity", "실행")
        click()

        try {
            if (tOrF != -1) { // 수정: tOrF가 -1이 아닌지 확인
                Log.d("orderCafeActivity", "tOrF != -1")
                Log.d("orderCafeActivity", "${tOrF + 1}")

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
                            innerList.add(menu.menuIdx.toString())

                            testDataSet.add(innerList)

                            Log.d("getMenuData testDataSet", "$testDataSet")

                            if (i == menuList.size) {
                                Log.d("getMenuData testDataSet", "$testDataSet")

                                // 마지막 메뉴 데이터를 처리한 경우 리사이클러뷰 초기화 및 어댑터 설정
                                val customAdapter = CustomAdapter(testDataSet, this)
                                recyclerView.adapter = customAdapter // 어댑터 설정
                                Log.d("orderCafeActivity", "for end")
                            }
                        }
                        Log.d("orderCafeActivity", "for go")
                    }
                }
                Log.d("orderCafeActivity", "requestManager end")
            }
        } catch (e: Exception) {
            Log.d("주문", "$e")
        }
    }

    private fun click() {
        binding.idOrderButton.setOnClickListener {
            val menuSum = binding.idOrderSum.text
            Log.d("pressedResponseButton", "menuSum: $menuSum")
            val menuText = menuOrNull
            Log.d("pressedResponseButton", "menuText: $menuText")
            val menuIdx = menuIdx
            Log.d("pressedResponseButton", "menuIdx: $menuIdx")

            try {
                menuText ?: return@setOnClickListener Toast.makeText(
                    this,
                    "메뉴를 선택 해 주세요.",
                    Toast.LENGTH_SHORT
                ).show()
                if (menuSum.isNullOrBlank() || menuSum.toString() == "0")
                    return@setOnClickListener Toast.makeText(
                        this,
                        "1 이상의 갯수를 입력해주세요.",
                        Toast.LENGTH_SHORT
                    ).show()

                for (i in 0 until  testDataSet.size) {
                    Log.d("OrderActivity", "testDataSet.size: ${testDataSet.size} $testDataSet \n\n ${formatter.format(menuPrizes!!*menuSum.toString().toInt())}원")
                    if (testDataSet[i].contains(menuText.toString())) {
                        val msgBuilder = AlertDialog.Builder(this).setCancelable(false)
                            .setTitle("주문하시겠습니까?")
                            .setMessage("$menuText $menuSum 개")
                            .setPositiveButton(
                                "주문"
                            ) { _, _ ->
                                Log.d("orderCafeActivity", "Order Confirm clicked menuIdx: $menuIdx")
                                if (menuIdx != null) {
                                    for (i in 0 until menuSum.toString().toInt()) {
                                        requestManager.postOrder(menuIdx.toInt(),
                                            getSharedPreferences("token", MODE_PRIVATE)
                                                .getString("accessToken", "토큰 없음").toString()) {
                                            Log.d("orderCafeActivity", "postOrderSring: $it")
                                        }
                                    }
                                    Toast.makeText(this, "주문이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .setNegativeButton(
                                "취소"
                            ) { _, _ -> }
                        val msgDlg = msgBuilder.create()
                        msgDlg.show()
                        break
                    }
                }
            } catch (e: Exception) {
                Log.d("pressedResponseButton", "",e)
            }

        }
    }

    fun setBuyMenu(menuName: String, menuIndex: String, menuPrize: Int) {
        binding.idOrderMenu.hint = menuName
        menuOrNull = menuName
        menuIdx = menuIndex
        menuPrizes = menuPrize
        Log.d("OrderCafeActivity", "setBuyMenu: $menuIdx")
        Log.d("OrderCafeActivity", "setBuyMenu menuOrNull: $menuOrNull")

    }

}


