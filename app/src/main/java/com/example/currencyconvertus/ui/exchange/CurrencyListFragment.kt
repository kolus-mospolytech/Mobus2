package com.example.currencyconvertus.ui.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconvertus.R
import com.example.currencyconvertus.databinding.FragmentCurrencyListBinding
import com.example.currencyconvertus.misc.CurrencyHolder

class CurrencyListFragment : Fragment() {
    private lateinit var binding: FragmentCurrencyListBinding
    var adapter: CurrencyListAdapter = CurrencyListAdapter(this)

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
        adapter.itemList = CurrencyHolder.createCurrencyList()
        setupRecycleView()
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
}