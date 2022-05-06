package com.example.stockcryptoapp.feature_stock_crypto.presentation

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.stockcryptoapp.R
import com.example.stockcryptoapp.databinding.FragmentQuoteListBinding


class QuoteListFragment : Fragment() {

    private lateinit var binding: FragmentQuoteListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuoteListBinding.inflate(inflater)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.appBar)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.iconSearch.setOnClickListener {
            findNavController().navigate(R.id.action_quoteListFragment_to_loginFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.tool_bar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.search -> {
                navigateToSearch()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToSearch() {
        findNavController().navigate(R.id.action_quoteListFragment_to_tickerSearchFragment)
    }
}