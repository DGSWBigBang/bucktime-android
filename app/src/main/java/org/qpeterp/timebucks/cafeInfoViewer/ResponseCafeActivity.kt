package org.qpeterp.timebucks.cafeInfoViewer

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.qpeterp.timebucks.ReservationRequest
import org.qpeterp.timebucks.adapter.TableAdapter
import org.qpeterp.timebucks.databinding.ActivityResponseCafeBinding
import org.qpeterp.timebucks.retrofit.UserRequestManager
import java.text.DecimalFormat

class ResponseCafeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityResponseCafeBinding.inflate(layoutInflater) }
    private val userRequestManager = UserRequestManager()
    private var confirm: Boolean = false
    private var tableList = ArrayList<ArrayList<String>>()
    private var tableIdx: String? = null
    private var tableOrNull: String? = null
    private var tablePrizes: Int? = null
    private var useTableTimeString: String? = null
    private var tOrF: Int? = null
    private val formatter: DecimalFormat = DecimalFormat("###,###")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        tOrF = intent.getIntExtra("tOrF", -1)

        setOnClickConfirmButton()
        clickResopnseButton()

       setRecyclerVeiw()

//        binding.responseButton.setOnClickListener {
//            userRequestManager.cafe(tOrF+1) {
//                val useTableTimeString = binding.useTableTime.text.toString().trim()
//                val useTableIdxString = binding.idResponseTable.text.toString().trim()
//                if (!confirm) {
//                    Toast.makeText(this, "확정이 되지 않았습니다.", Toast.LENGTH_SHORT).show()
//                } else if (useTableIdxString.isNullOrBlank()){
//                    Toast.makeText(this, "테이블 번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
//                } else {
//                    val useTableTime = useTableTimeString.toInt()
//                    val useTableIdx = useTableIdxString.toInt()
//
//                    if (useTableTime == 0 || useTableIdx == 0) {
//                        Toast.makeText(this, "테이블 이용 시간이 0입니다.", Toast.LENGTH_SHORT).show()
//                    } else if (useTableIdx > it.size) {
//                        Log.d("userRequestManager Cafe It :", "${it.size}")
//                        Toast.makeText(this, "테이블 번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show()
//                    } else {
//                        val reservationRequest = ReservationRequest(useTableIdx, useTableTime)
//                        Log.d("짜증나는 에러 1", "화가난다")
//                        userRequestManager.reservation(reservationRequest, getSharedPreferences("token", MODE_PRIVATE).getString("accessToken", "토큰 없음").toString()) {
//                            Log.d("짜증나는 에러 2", "화가난다2")
//                            if (it.message == "예약 완료") {
//                                val nextActivity = Intent(this, OrderCafeActivity::class.java)
//                                nextActivity.putExtra("tOrF", tOrF) // tOrF 변수를 인텐트에 추가
//                                startActivity(nextActivity)
//
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }

    private fun setOnClickConfirmButton() {
        binding.confirmButton.setOnClickListener {
            useTableTimeString = binding.useTableTime.text.toString().trim()
            if (binding.checkedToUseTable.hint.equals("사용할 테이블을 선택해주세요.")){
                Toast.makeText(this, "테이블을 선택해 주세요.", Toast.LENGTH_SHORT).show()
            }

            else if (useTableTimeString.isNullOrBlank() || useTableTimeString!!.toInt() == 0) {
                Toast.makeText(this, "테이블 이용 시간이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                this.confirm = true
                Toast.makeText(this, "확정되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clickResopnseButton() {
        binding.responseButton.setOnClickListener {
            val tableText = tableOrNull
            Log.d("pressedResponseButton", "menuText: $tableText")
            val tableIdx = tableIdx
            Log.d("pressedResponseButton", "menuIdx: $tableIdx")

            try {
                if (confirm) {
                    for (i in 0 until  tableList.size) {
                        if (tableList[i].contains(tableText.toString())) {
                            val msgBuilder = AlertDialog.Builder(this).setCancelable(false)
                                .setTitle("결제하시겠습니까?")
                                .setMessage("$tableText ${binding.useTableTime.text}시간\n\n ${formatter.format(tablePrizes!! * binding.useTableTime.text.toString().toInt())}원")
                                .setPositiveButton(
                                    "확정"
                                ) { _, _ ->
                                    Log.d("orderCafeActivity", "Order Confirm clicked menuIdx: $tableIdx")
                                    if (tableIdx != null) {
                                        val reservationRequest = ReservationRequest(tableIdx.toInt(), useTableTimeString!!.toInt())
                                        Log.d("짜증나는 에러 1", "화가난다")
                                        userRequestManager.reservation(reservationRequest, getSharedPreferences("token", MODE_PRIVATE).getString("accessToken", "토큰 없음").toString()) {
                                            Log.d("짜증나는 에러 2", "화가난다2")
                                            if (it.message == "예약 완료") {
                                                val nextActivity = Intent(this, OrderCafeActivity::class.java)
                                                nextActivity.putExtra("tOrF", tOrF) // tOrF 변수를 인텐트에 추가
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
                else {
                    Toast.makeText(this, "먼저 확정해 주세요.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.d("pressedResponseButton", "",e)
            }

        }
    }

    private fun setRecyclerVeiw() {
        userRequestManager.cafe(tOrF!!+1) {
            binding.tableMaxNum.text = "테이블의 최대 번호는 ${it.size}번 입니다."
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