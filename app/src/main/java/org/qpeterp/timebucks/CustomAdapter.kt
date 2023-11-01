package org.qpeterp.timebucks

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.qpeterp.timebucks.dataClass.CafeReservation
import org.qpeterp.timebucks.databinding.ItemCafeFragmentBinding

class CustomAdapter(private var dataSet: Array<String>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding: ItemCafeFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val numTextView: TextView = binding.idCafeNum
//        private val nameTextView: TextView = binding.idCafeName
        private val timeStartTextView: TextView = binding.idCafeTimeStart
        private val timeEndTextView: TextView = binding.idCafeTimeEnd
        private val deskIdxTextView: TextView = binding.idCafeDeskIdx
        private val usedTextView: TextView = binding.idCafeUsed

        fun bind(
            rezIdx: String,
            deskName: String,
            startTime: String,
            finishTime: String,
            deskIdx: String,
            used: String
        ) {
            numTextView.text = rezIdx
//            nameTextView.text = deskName
            timeStartTextView.text = startTime
            timeEndTextView.text = finishTime
            deskIdxTextView.text = deskIdx
            usedTextView.text = used
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rezIdx = dataSet[0]
        val deskName = dataSet[1]
        val startTime = dataSet[2]
        val finishTime = dataSet[3]
        val deskIdx = dataSet[5]
        val used = dataSet[6]

        holder.bind(rezIdx, deskName, startTime, finishTime, deskIdx, used)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCafeFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = dataSet.size
}
