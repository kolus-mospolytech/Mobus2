package com.example.currencyconvertus.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconvertus.R
import com.example.currencyconvertus.RepositoryDependency
import com.example.currencyconvertus.databinding.FragmentHistoryBinding
import com.example.currencyconvertus.ui.RecyclerViewItemDecoration

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private var adapter: HistoryAdapter = HistoryAdapter()
    private val viewModel = RepositoryDependency.viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()

        viewModel.history.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.updateListItem(it.entries)
            }
        }
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