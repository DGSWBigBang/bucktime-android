package org.qpeterp.timebucks

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.qpeterp.timebucks.dataClass.CafeReservation
import org.qpeterp.timebucks.databinding.ItemCafeFragmentBinding

class CustomAdapter(private var dataSet: ArrayList<ArrayList<String>>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: ItemCafeFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val menuNames: TextView = binding.menuName
        private val menuInfos: TextView = binding.menuInfo
        private val menuPrizes: TextView = binding.menuPrize

        fun bind(
            menuName: String,
            menuInfo: String,
            menuPrize: String
        ) {
            Log.d("onBindViewHolder", "dataSet: ${dataSet}")

            menuNames.text = menuName
            menuInfos.text = menuInfo
            menuPrizes.text = menuPrize
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("onBindViewHolder", "dataSet: ${dataSet}")
        Log.d("onBindViewHolder", "position: ${position}")
        val menuName = dataSet[position][0]
        val menuInfo = dataSet[position][1]
        val menuPrize = dataSet[position][2]

        holder.bind(menuName, menuInfo, menuPrize)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("onBindViewHolder", "dataSet: ${dataSet}")
        val binding = ItemCafeFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return ViewHolder(binding)
    }

    override fun getItemCount() = dataSet.size
}
