package org.qpeterp.timebucks.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.qpeterp.timebucks.cafeInfoViewer.ResponseCafeActivity
import org.qpeterp.timebucks.databinding.ItemTableRecyclerviewBinding
import java.text.DecimalFormat


class TableAdapter(private var dataSet: ArrayList<ArrayList<String>>, private val responseCafeActivity: ResponseCafeActivity) :
    RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemTableRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val tableNames: TextView = binding.tableName
        private val tablePrizes: TextView = binding.tablePrize

        fun bind(
            tableName: String,
            tablePrize: String,
            tableIndex: String

        ) {
            Log.d("onBindViewHolder table", "dataSet: ${dataSet}")

            try {
                tableNames.text = tableName
                tablePrizes.text = "${tablePrize}원"

                itemView.setOnClickListener {
                    responseCafeActivity.setBuyTable(tableName, tableIndex, tablePrize.toInt())
                }
            } catch (e:Exception) {
                Log.e("TableAdapter", "",e)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableAdapter.ViewHolder {
        Log.d("onBindViewHolder table", "dataSet: ${dataSet}")
        val binding = ItemTableRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TableAdapter.ViewHolder, position: Int) {
        Log.d("onBindViewHolder table", "dataSet: $dataSet")
        Log.d("onBindViewHolder table", "position: $position")

        val tableName = dataSet[position][0]
        val tablePrize = dataSet[position][1]
        val tableIndex = dataSet[position][2]

        holder.bind(tableName, tablePrize, tableIndex)
    }

    override fun getItemCount(): Int = dataSet.size
}