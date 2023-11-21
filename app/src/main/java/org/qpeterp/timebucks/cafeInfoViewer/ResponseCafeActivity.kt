package org.qpeterp.timebucks.cafeInfoViewer

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import android.widget.NumberPicker.OnValueChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.qpeterp.timebucks.ReservationRequest
import org.qpeterp.timebucks.adapter.TableAdapter
import org.qpeterp.timebucks.databinding.ActivityResponseCafeBinding
import org.qpeterp.timebucks.retrofit.RequestManager
import org.qpeterp.timebucks.retrofit.UserRequestManager
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date

class ResponseCafeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityResponseCafeBinding.inflate(layoutInflater) }
    private val userRequestManager = UserRequestManager()
    private var tableList = ArrayList<ArrayList<String>>()
    private var tableIdx: String? = null
    private var tableOrNull: String? = null
    private var tablePrizes: Int? = null
    private var useTableTimeString: String? = null
    private var tOrF: Int? = null
    private val formatter: DecimalFormat = DecimalFormat("###,###")
    private var numberPicker: NumberPicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        tOrF = intent.getIntExtra("tOrF", -1)

        numberPicker = binding.useTableTime

        val now = System.currentTimeMillis()
        val date = Date(now)
        val dateFormat = SimpleDateFormat("HH")

        val getTime = dateFormat.format(date)
        val hour = getTime.toInt()

        Log.d("now time mm", "$getTime")
        var closeTime = 0

        RequestManager.getCafeInfo {
            if (it[tOrF!!].closeTime == "0000") {
                closeTime = 24
            }
            else {
                closeTime = it[tOrF!!].closeTime.slice(0..1).toInt()
            }
            numberPicker!!.maxValue = closeTime - hour

        }
        numberPicker!!.value = 1
        numberPicker!!.minValue = 1



        clickResopnseButton()

       setRecyclerVeiw()
    }


    private fun clickResopnseButton() {
        numberPicker!!.setWrapSelectorWheel(false) //최대값 or 최소값에서 멈출지 넘어갈지

        numberPicker!!.setOnValueChangedListener(OnValueChangeListener { picker, oldVal, newVal ->
            useTableTimeString = newVal.toString()
            Log.d("ResponseCafeActivity", "useTableTimeString : $useTableTimeString")
        })

        binding.responseButton.setOnClickListener {
            val tableText = tableOrNull
            Log.d("pressedResponseButton", "menuText: $tableText")
            val tableIdx = tableIdx
            Log.d("pressedResponseButton", "menuIdx: $tableIdx")

            try {
                if (binding.checkedToUseTable.hint.equals("사용할 테이블을 선택해주세요.")) {
                    Toast.makeText(this, "테이블을 선택해 주세요.", Toast.LENGTH_SHORT).show()
                }
                else {
                    for (i in 0 until tableList.size) {
                        if (tableList[i].contains(tableText.toString())) {
                            val msgBuilder = AlertDialog.Builder(this).setCancelable(false)
                                .setTitle("결제하시겠습니까?")
                                .setMessage(
                                    "$tableText ${useTableTimeString}시간\n\n ${
                                        formatter.format(
                                            tablePrizes!! * useTableTimeString!!.toInt()
                                        )
                                    }원"
                                )
                                .setPositiveButton(
                                    "확정"
                                ) { _, _ ->
                                    Log.d(
                                        "orderCafeActivity",
                                        "Order Confirm clicked menuIdx: $tableIdx"
                                    )
                                    if (tableIdx != null) {
                                        val reservationRequest = ReservationRequest(
                                            tableIdx.toInt(),
                                            useTableTimeString!!.toInt()
                                        )
                                        Log.d("짜증나는 에러 1", "화가난다")
                                        userRequestManager.reservation(
                                            reservationRequest,
                                            getSharedPreferences(
                                                "token",
                                                MODE_PRIVATE
                                            ).getString("accessToken", "토큰 없음").toString()
                                        ) {
                                            Log.d("짜증나는 에러 2", "화가난다2")
                                            if (it.message == "예약 완료") {
                                                val nextActivity =
                                                    Intent(this, OrderCafeActivity::class.java)
                                                nextActivity.putExtra(
                                                    "tOrF",
                                                    tOrF
                                                ) // tOrF 변수를 인텐트에 추가
                                                startActivity(nextActivity)
                                                finish()
                                            }
                                        }
                                        Toast.makeText(this, "예약되었습니다.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                .setNegativeButton(
                                    "취소"
                                ) { _, _ ->
                                    Toast.makeText(this, "취소되었습니다.", Toast.LENGTH_SHORT).show()
                                }
                            val msgDlg = msgBuilder.create()
                            msgDlg.show()
                            break
                        }
                    }
                }

            } catch (e: Exception) {
                Log.d("pressedResponseButton", "",e)
            }

        }
    }

    private fun setRecyclerVeiw() {
        userRequestManager.cafe(tOrF!!+1) {
            if (it.size == 0) {
                val msgBuilder = AlertDialog.Builder(this).setCancelable(false)
                    .setTitle("좌석이 존재하지 않습니다.")
                    .setMessage("현재 이 카페의 좌석정보가 비어있습니다. 이용에 불편을 드려 죄송합니다.")
                    .setPositiveButton(
                        "확인"
                    ) { _, _ -> finish()}

                val msgDlg = msgBuilder.create()
                msgDlg.show()

                return@cafe
            }

            val recyclerView: RecyclerView = binding.tablePageRecyclerView
            val linearLayoutManager = GridLayoutManager(this as Context, 2)
            recyclerView.layoutManager = linearLayoutManager // LayoutManager 설정

            for (i in 0 until it.size) {
                val innerList = ArrayList<String>()

                innerList.add(it[i].deskName)
                innerList.add(it[i].price.toString())
                innerList.add(it[i].deskIdx.toString())

                tableList.add(innerList)
            }

            Log.d("getCafeTableData tableList", "$tableList")

            // 마지막 메뉴 데이터를 처리한 경우 리사이클러뷰 초기화 및 어댑터 설정
            val tableAdapter = TableAdapter(tableList, this)
            recyclerView.adapter = tableAdapter // 어댑터 설정
            Log.d("orderCafeActivity", "for end")
        }
    }

    fun setBuyTable(tableName: String, tableIndex: String, tablePrize: Int) {
        binding.checkedToUseTable.hint = tableName
        tableOrNull = tableName
        tableIdx = tableIndex
        tablePrizes = tablePrize
    }

}