package com.example.stockcryptoapp.feature_stock_crypto.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stockcryptoapp.databinding.SearchItemBinding
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.MatchedResult

class SearchResultAdapter(
    private var matchedResults: List<MatchedResult> = emptyList(),
    val onItemClicked: (MatchedResult) -> Unit

) : RecyclerView.Adapter<SearchResultAdapter.Viewholder>() {

    class Viewholder(private val binding: SearchItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(matchResult:MatchedResult, onItemClicked: (MatchedResult) -> Unit){
            binding.tvTicker.text = matchResult.ticker
            binding.tvName.text = matchResult.name

            binding.root.setOnClickListener {
                onItemClicked(matchResult)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        return Viewholder(
            SearchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val dataItem = matchedResults[position]
        holder.bind(dataItem, onItemClicked)
    }

    override fun getItemCount(): Int {
        return matchedResults.size
    }

    fun updateResultList(resultList: List<MatchedResult>?){

        matchedResults = resultList ?: emptyList()
        notifyDataSetChanged()

    }
}