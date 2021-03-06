package com.example.stockcryptoapp.feature_stock_crypto.presentation

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockcryptoapp.R
import com.example.stockcryptoapp.databinding.FragmentTickerSearchBinding
import com.example.stockcryptoapp.feature_stock_crypto.presentation.adapter.SearchResultAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TickerSearchFragment : Fragment() {

    private lateinit var binding: FragmentTickerSearchBinding

    private val viewModel: StockViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTickerSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvNoSearchResult.text = "No search result"

        binding.appBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.etSymbolSearch.setOnKeyListener { _, keyCode, keyEvent ->
            if ((keyEvent.action == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER)){

                viewModel.searchKeyword(binding.etSymbolSearch.text.toString())
                true
            } else false
        }

        val adapter = SearchResultAdapter(
            onItemClicked = {
                val action = TickerSearchFragmentDirections.actionTickerSearchFragmentToTickerDetailFragment(symbol = it.ticker)
                findNavController().navigate(action)
            },
            setImageIcon = {
                when (viewModel.isStockFavorite(it.ticker)){
                    true -> R.drawable.ic_baseline_favorite_red
                    else -> R.drawable.ic_baseline_favorite_border_dark
                }
            },
            onButtonClicked = {
                if (viewModel.isStockFavorite(it.ticker)){
                    viewModel.removeStockFromFavorite(it.ticker)
                    R.drawable.ic_baseline_favorite_border_dark
                } else {
                    viewModel.addStockToFavorite(it.ticker){
                        Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show()
                    }
                    R.drawable.ic_baseline_favorite_red
                }
            }
        )

        binding.searchResultRecyclerView.adapter = adapter
        binding.searchResultRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.searchResult.observe(viewLifecycleOwner){
            if (it.size == 0){
                binding.tvNoSearchResult.text = "Unable to fetch result, please try again"
            }else {
                binding.tvNoSearchResult.text = ""
                adapter.updateResultList(it)
            }

        }
    }
}