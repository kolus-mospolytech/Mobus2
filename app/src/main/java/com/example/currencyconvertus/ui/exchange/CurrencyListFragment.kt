package com.example.currencyconvertus.ui.exchange

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconvertus.R
import com.example.currencyconvertus.RepositoryDependency
import com.example.currencyconvertus.databinding.FragmentCurrencyListBinding
import com.example.currencyconvertus.ui.CurrencyViewModel

class CurrencyListFragment : Fragment() {
    private lateinit var binding: FragmentCurrencyListBinding
    private val viewModel: CurrencyViewModel = RepositoryDependency.viewModel
    private var adapter: CurrencyListAdapter = CurrencyListAdapter(this::toggleFavorite,)

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

}