package com.example.currencyconvertus.ui.history

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconvertus.databinding.HistoryListItemBinding
import com.example.currencyconvertus.ui.model.HistoryEntryUI
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter :
    RecyclerView.Adapter<HistoryAdapter.Holder>() {
    // Добавленные переменные
    private var listItem: MutableList<HistoryEntryUI> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            HistoryListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val item: HistoryEntryUI = listItem[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    inner class Holder internal constructor(private val binding: HistoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("NotifyDataSetChanged", "SimpleDateFormat")
        fun bind(currencyRates: HistoryEntryUI) =
            binding.run {
                binding.historyName1.text = currencyRates.currency1
                binding.historyValue1.text = String.format("%.2f", currencyRates.rate1.toDouble())
                binding.historyCount1.text = currencyRates.value1

                val date = currencyRates.timestamp
                val sdf = SimpleDateFormat("dd-MM-yyyy")
                sdf.timeZone = TimeZone.getTimeZone("W-SU")
                val text: String = sdf.format(date)
                binding.historyDate.text = text

                binding.historyName2.text = currencyRates.currency2
                binding.historyValue2.text = String.format("%.2f", currencyRates.rate2.toDouble())
                binding.historyCount2.text = currencyRates.value2
            }
    }

    fun updateListItem(newListItem: MutableList<HistoryEntryUI>) {
        listItem = newListItem
    }
}