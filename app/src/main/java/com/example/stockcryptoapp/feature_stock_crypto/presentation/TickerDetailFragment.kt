package com.example.stockcryptoapp.feature_stock_crypto.presentation

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.stockcryptoapp.R
import com.example.stockcryptoapp.databinding.FragmentTickerDetailBinding
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.CompanySummary
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "TickerDetailFragment"

@AndroidEntryPoint
class TickerDetailFragment : Fragment() {

    private val navigationArgs: TickerDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentTickerDetailBinding

    private val viewModel: StockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTickerDetailBinding.inflate(inflater)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.appBar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val symbol = navigationArgs.symbol
        viewModel.retrieveCompanyInfo(symbol)
        viewModel.companyInfo.observe(viewLifecycleOwner) { bindView(it)}

        binding.appBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.tool_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.search -> {
                findNavController().navigate(R.id.action_tickerDetailFragment_to_tickerSearchFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun bindView(summary: CompanySummary){

        binding.tvName.text = summary.name
        binding.tvSymbol.text = summary.symbol
        binding.tvAddress.text = summary.address
        binding.tvDescription.text = summary.description

    }


}