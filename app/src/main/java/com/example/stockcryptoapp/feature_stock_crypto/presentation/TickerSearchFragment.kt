package com.example.stockcryptoapp.feature_stock_crypto.presentation

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockcryptoapp.R
import com.example.stockcryptoapp.databinding.FragmentTickerSearchBinding
import com.example.stockcryptoapp.feature_stock_crypto.presentation.adapter.SearchResultAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TickerSearchFragment : Fragment() {

    private lateinit var binding: FragmentTickerSearchBinding

    private val viewModel: StockViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentTickerSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.appBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.etSymbolSearch.setOnKeyListener { _, keyCode, keyEvent ->
            if ((keyEvent.action == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)){
                Toast.makeText(context, "Enter pressed", Toast.LENGTH_SHORT).show()
                viewModel.searchKeyword(binding.etSymbolSearch.text.toString())
                true
            } else false
        }

        val adapter = SearchResultAdapter{
            val action = TickerSearchFragmentDirections.actionTickerSearchFragmentToTickerDetailFragment(symbol = it.ticker)
            findNavController().navigate(action)

        }
        binding.searchResultRecyclerView.adapter = adapter
        binding.searchResultRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.searchResult.observe(viewLifecycleOwner){
            adapter.updateResultList(it)
        }
    }
}