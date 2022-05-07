package com.example.stockcryptoapp.feature_stock_crypto.presentation

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stockcryptoapp.R
import com.example.stockcryptoapp.databinding.FragmentQuoteListBinding
import com.example.stockcryptoapp.feature_login.domain.UserManager
import com.example.stockcryptoapp.feature_stock_crypto.presentation.adapter.QuoteListAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuoteListFragment : Fragment() {

    private lateinit var binding: FragmentQuoteListBinding

    private val viewModel: StockViewModel by activityViewModels()

    @Inject
    lateinit var userManager: UserManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        viewModel.addFavoriateList(userManager.getUserFavoriateList())

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

//        if (!userManager.isUserLoggedIn()){
//            findNavController().navigate(R.id.action_quoteListFragment_to_loginFragment)
//        }



        val adapter = QuoteListAdapter {
            val action =
                QuoteListFragmentDirections.actionQuoteListFragmentToTickerDetailFragment(symbol = it.ticker)
            findNavController().navigate(action)
        }

        binding.quoteListRecyclerView.adapter = adapter
        binding.quoteListRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.favoriateStock.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_quoteListFragment_to_tickerSearchFragment)
        }

    }

}