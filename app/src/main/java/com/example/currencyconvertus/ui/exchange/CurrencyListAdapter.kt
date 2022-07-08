package com.example.currencyconvertus.ui.exchange

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconvertus.databinding.CurrencyListItemBinding
import com.example.currencyconvertus.ui.model.CurrencyUI
import kotlin.reflect.KFunction1

class CurrencyListAdapter(private val setFavorite: KFunction1<String, Unit>,) :
    RecyclerView.Adapter<CurrencyListAdapter.Holder>() {
    private var itemList: MutableList<CurrencyUI> = mutableListOf()
    private var base: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            CurrencyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val currentListItem = itemList[position]
        holder.bind(currentListItem, setFavorite)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class Holder internal constructor(private val binding: CurrencyListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            currentListItem: CurrencyUI,
            favoriteToggle: KFunction1<String, Unit>
        ) = binding.run {
            currency.text = currentListItem.name
            currencyRate.text = currentListItem.value + " " + base
            favorite.isChecked = currentListItem.favorite

            favorite.setOnClickListener{
                favoriteToggle(currentListItem.name)
            }
        }
    }

    fun updateData(newItemList: MutableList<CurrencyUI>, newBase: String) {
        itemList = newItemList
        base = newBase
    }
}