package com.example.stockcryptoapp.feature_stock_crypto.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.stockcryptoapp.R
import com.example.stockcryptoapp.databinding.FragmentTickerDetailBinding
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.CompanySummary
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TickerDetailFragment : Fragment() {

    private val navigationArgs: TickerDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentTickerDetailBinding

    private val viewModel: StockViewModel by activityViewModels()

    private var isStockFavorite: Boolean = false
    private lateinit var summary: CompanySummary

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTickerDetailBinding.inflate(inflater)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.appBar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val symbol = navigationArgs.symbol

        isStockFavorite = viewModel.isStockFavorite(symbol)
        updateFavoriteIcon()

        viewModel.retrieveCompanyInfo(symbol)
        viewModel.companyInfo.observe(viewLifecycleOwner) {
            summary = it
            bindView()
        }

        binding.appBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.ivFavorite.setOnClickListener {

            if (isStockFavorite){
                viewModel.removeStockFromFavorite(ticker = symbol)
            }
            else {
                viewModel.addStockToFavorite(summary.symbol)
            }

            isStockFavorite = !isStockFavorite
            updateFavoriteIcon()
        }

        binding.ivSearch.setOnClickListener {
            findNavController().navigate(R.id.action_tickerDetailFragment_to_tickerSearchFragment)
        }

        binding.tvAddress.setOnClickListener {
            binding.tvAddress.text?.let { address ->
                val mapUri = Uri.Builder().scheme("geo").path("0,0")
                    .appendQueryParameter("q", address.toString())

                val openMapIntent = Intent(Intent.ACTION_VIEW, mapUri.build())
                startActivity(openMapIntent)
            }

        }
    }

    private fun bindView(){

        binding.tvName.text = summary.name
        binding.tvSymbol.text = summary.symbol
        binding.tvAddress.text = summary.address
        binding.tvDescription.text = summary.description

    }

    private fun updateFavoriteIcon(){
        if (isStockFavorite){
            binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_red)
        } else {
            binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_white)
        }
    }


}