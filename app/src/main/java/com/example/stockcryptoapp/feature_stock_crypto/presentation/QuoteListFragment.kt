package com.example.stockcryptoapp.feature_stock_crypto.presentation

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
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

    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        if (!userManager.isUserLoggedIn()){
            findNavController().navigate(R.id.action_quoteListFragment_to_loginFragment)
        } else {
            viewModel.addFavoriateList(userManager.retrieveUserFavorite().toList())
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentQuoteListBinding.inflate(inflater)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.appBar)

        binding.appBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
//            drawerLayout
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->

            when(menuItem.itemId){
                R.id.search_item -> navigateToSearchFragment()
                R.id.sign_out_item -> {
                    userManager.logout()
                    findNavController().navigate(R.id.action_quoteListFragment_to_loginFragment)
                }
            }

            menuItem.isChecked = true
            binding.drawerLayout.close()
            true

        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = QuoteListAdapter {
            val action =
                QuoteListFragmentDirections.actionQuoteListFragmentToTickerDetailFragment(symbol = it.ticker)
            findNavController().navigate(action)
        }

        binding.quoteListRecyclerView.adapter = adapter
        binding.quoteListRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.favoriateStock.observe(viewLifecycleOwner) { favoriateStocks ->
            adapter.submitList(favoriateStocks)
            userManager.updateUserFavoriate(favoriateStocks.map { it.ticker }.toSet())
        }

        binding.search.setOnClickListener {
            navigateToSearchFragment()

        }
    }

    private fun navigateToSearchFragment(){
        findNavController().navigate(R.id.action_quoteListFragment_to_tickerSearchFragment)
    }


}