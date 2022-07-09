package com.example.currencyconvertus.ui.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.example.currencyconvertus.ActivityCallBack
import com.example.currencyconvertus.RepositoryDependency
import com.example.currencyconvertus.databinding.FragmentExchangeBinding
import com.example.currencyconvertus.ui.model.HistoryEntryUI
import com.example.currencyconvertus.ui.model.HistoryUIModel
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import java.util.*

class ExchangeFragment : Fragment() {
    private lateinit var binding: FragmentExchangeBinding
    private val viewModel = RepositoryDependency.viewModel
    private val listCurrencies = mutableListOf<String>()
    private var activeValue = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExchangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.exchange.observe(viewLifecycleOwner) {
            binding.value1.hint = it.currency1.name
            binding.value1.editText?.setText(it.amount1.toString())

            binding.currency1.editText?.setText(it.currency1.name)
            (binding.currency1.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(
                listCurrencies.toTypedArray()
            )
            binding.value2.hint = it.currency2.name
            binding.value2.editText?.setText(String.format("%.2f", it.amount2))

            binding.currency2.editText?.setText(it.currency2.name)
            (binding.currency2.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(
                listCurrencies.toTypedArray()
            )
        }

        viewModel.rates.observe(viewLifecycleOwner) { rate ->
            listCurrencies.clear()
            rate.rates.forEach {
                listCurrencies.add(it.name)
                (binding.currency1.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(
                    listCurrencies.toTypedArray()
                )
                (binding.currency2.editText as? MaterialAutoCompleteTextView)?.setSimpleItems(
                    listCurrencies.toTypedArray()
                )
            }
        }

        binding.applyExchange.setOnClickListener {
            val rates = viewModel.rates.value?.rates ?: return@setOnClickListener
            val base = viewModel.rates.value?.base ?: return@setOnClickListener
            val rate1 = rates.firstOrNull { it.name == binding.currency1.editText?.text.toString() }
                ?: return@setOnClickListener
            val rate2 = rates.firstOrNull { it.name == binding.currency2.editText?.text.toString() }
                ?: return@setOnClickListener
            val value1 = binding.value1.editText?.text.toString()
            val value2 = binding.value2.editText?.text.toString()

            val history = viewModel.history.value

            val historyList = history?.entries ?: mutableListOf()
//            val dateAt = history?.startDate ?: System.currentTimeMillis()
//            val dateFrom = history?.endDate ?: System.currentTimeMillis()

            val timestamp = Date(System.currentTimeMillis())

            historyList.add(
                HistoryEntryUI(
                    timestamp,
                    rate1.name,
                    rate1.value,
                    value1,
                    rate2.name,
                    rate2.value,
                    value2,
                    base
                )
            )

            viewModel.addHistoryEntry(
                timestamp,
                rate1.name,
                rate1.value.toDouble(),
                value1.toDouble(),
                rate2.name,
                rate2.value.toDouble(),
                value2.toDouble(),
                base
            )
            viewModel.history.postValue(
                HistoryUIModel(
                    historyList,
                )
            )


            val activityCallBack = requireActivity() as ActivityCallBack
            activityCallBack.navigateTo(2)
        }

        binding.value1.addOnEditTextAttachedListener { it ->
            it.editText?.doAfterTextChanged {
                if (activeValue == 0) {
                    activeValue = 1
                }
                if (activeValue == 2) {
                    activeValue = 0
                    return@doAfterTextChanged
                }
                try {
                    if (it.toString() == "") {
                        return@doAfterTextChanged
                    }
                } catch (text: Throwable) {
                    return@doAfterTextChanged
                }
                setNewValueRate("count1", it.toString().toDouble())
            }
        }
        binding.value2.addOnEditTextAttachedListener { it ->
            it.editText?.setOnClickListener {
                activeValue = 2
            }
            it.editText?.doAfterTextChanged {
                if (activeValue == 0) {
                    activeValue = 2
                }
                if (activeValue == 1) {
                    activeValue = 0
                    return@doAfterTextChanged
                }
                try {
                    if (it.toString() == "") {
                        return@doAfterTextChanged
                    }
                } catch (text: Throwable) {
                    return@doAfterTextChanged
                }
                setNewValueRate("count2", it.toString().toDouble())
            }
        }

        binding.currency1.addOnEditTextAttachedListener { it ->
            it.editText?.doAfterTextChanged {
                setNewValueRate("value1", it.toString())
            }
        }
        binding.currency2.addOnEditTextAttachedListener { it ->
            it.editText?.doAfterTextChanged {
                setNewValueRate("value2", it.toString())
            }
        }
    }

    private fun setNewValueRate(type: String, name: String) {
        when (type) {
            "value1" -> {
                val rates = viewModel.rates.value?.rates ?: return
                val rate = rates.find { it.name == name } ?: return
                val count = binding.value1.editText?.text.toString().toDouble()
                val exRate =
                    rates.find { it.name == binding.currency2.editText?.text.toString() }
                        ?: return

                activeValue = 1

                val newCount = String.format("%.2f", (count * rate.value.toDouble()) / exRate.value.toDouble())

                binding.value2.editText?.setText(newCount)
                binding.value1.hint = rate.name
            }
            "value2" -> {
                val rates = viewModel.rates.value?.rates ?: return
                val exRate = rates.find { it.name == name } ?: return
                val rate = rates.find { it.name == binding.currency1.editText?.text.toString() }
                    ?: return
                val count = binding.value2.editText?.text.toString().toDouble()

                activeValue = 2

                val newCount = String.format("%.2f", (count * rate.value.toDouble()) / exRate.value.toDouble())

                binding.value1.editText?.setText(newCount)
                binding.value2.hint = exRate.name
            }
        }
    }

    private fun setNewValueRate(type: String, count: Double) {
        val rates = viewModel.rates.value?.rates ?: return
        val rate =
            rates.find { it.name == binding.currency1.editText?.text.toString() } ?: return
        val exRate =
            rates.find { it.name == binding.currency2.editText?.text.toString() } ?: return

        when (type) {
            "count1" -> {
                val newCount = String.format("%.2f", (count * rate.value.toDouble()) / exRate.value.toDouble())

                binding.value2.editText?.setText(newCount)
            }
            "count2" -> {
                val newCount = String.format("%.2f", (count * exRate.value.toDouble()) / rate.value.toDouble())

                binding.value1.editText?.setText(newCount)
            }
        }
    }
}