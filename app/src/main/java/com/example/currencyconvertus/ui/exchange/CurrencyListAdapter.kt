package com.example.currencyconvertus.ui.exchange

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconvertus.databinding.CurrencyListItemBinding
import com.example.currencyconvertus.misc.CurrencyListItem

class CurrencyListAdapter(private val listItem: CurrencyListFragment) :
    RecyclerView.Adapter<CurrencyListAdapter.Holder>() {
    var itemList: List<CurrencyListItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            CurrencyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentListItem = itemList[position]
        holder.bind(currentListItem, listItem)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder internal constructor(private val binding: CurrencyListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            currentListItem: CurrencyListItem,
            listItem: CurrencyListFragment
        ) = binding.run {
            currency.text = currentListItem.currency
            currencyRate.text = currentListItem.rate
            favorite.isChecked = currentListItem.favorite
        }
    }
}