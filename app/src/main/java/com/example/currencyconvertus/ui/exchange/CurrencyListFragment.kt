package com.example.currencyconvertus.ui.exchange

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconvertus.ActivityCallBack
import com.example.currencyconvertus.R
import com.example.currencyconvertus.RepositoryDependency
import com.example.currencyconvertus.databinding.FragmentCurrencyListBinding
import com.example.currencyconvertus.ui.CurrencyViewModel
import com.example.currencyconvertus.ui.RecyclerViewItemDecoration
import com.example.currencyconvertus.ui.model.CurrencyUI
import com.example.currencyconvertus.ui.model.ExchangeUI

class CurrencyListFragment : Fragment() {
    private lateinit var binding: FragmentCurrencyListBinding
    private val viewModel: CurrencyViewModel = RepositoryDependency.viewModel
    private var adapter: CurrencyListAdapter =
        CurrencyListAdapter(this::toggleFavorite, this::openExchangeFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()

        viewModel.rates.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.updateData(it.rates, it.base)
                Log.d("MY_TAG", "${it.rates}")
                adapter.notifyDataSetChanged()
            }
        }
//        viewModel.sortCurrencies()
    }

    private fun setupRecycleView() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            RecyclerViewItemDecoration(
                this.requireContext(),
                R.drawable.divider
            )
        )
    }

    private fun toggleFavorite(name: String) {
        val currency = viewModel.rates.value?.rates?.firstOrNull { it.name == name } ?: return

        viewModel.toggleFavorite(name, currency.favorite)
    }

    private fun openExchangeFragment(currency: CurrencyUI) {
        val favCurrency = viewModel.rates.value?.rates?.firstOrNull { it.name != currency.name && it.favorite }
            ?: viewModel.rates.value?.rates?.firstOrNull { it.name == "RUB" } ?: return

        viewModel.exchange.postValue(
            ExchangeUI(
                amount1 = 1.0,
                currency1 = currency,
                currency2 = favCurrency,
            )
        )

        val activityCallBack = requireActivity() as ActivityCallBack
        activityCallBack.navigateTo(1)
    }
}