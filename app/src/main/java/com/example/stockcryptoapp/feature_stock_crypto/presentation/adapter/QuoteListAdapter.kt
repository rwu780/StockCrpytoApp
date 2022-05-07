package com.example.stockcryptoapp.feature_stock_crypto.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.stockcryptoapp.databinding.QuoteListItemBinding
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.FavoriateStock

class QuoteListAdapter(
   private val onItemClick: (FavoriateStock) -> Unit

) : androidx.recyclerview.widget.ListAdapter<FavoriateStock, QuoteListAdapter.ViewHolder>(DiffUtilCallback) {

    companion object {
        private val DiffUtilCallback = object : DiffUtil.ItemCallback<FavoriateStock>(){
            override fun areItemsTheSame(
                oldItem: FavoriateStock,
                newItem: FavoriateStock
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: FavoriateStock,
                newItem: FavoriateStock
            ): Boolean {
                return oldItem.ticker == newItem.ticker
            }

        }
    }


    class ViewHolder(private val binding: QuoteListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(dataItem: FavoriateStock, onItemClick: (FavoriateStock) -> Unit){
            binding.tvTicker.text = dataItem.ticker
            binding.tvName.text = dataItem.name
            binding.tvChange.text = dataItem.changePercent.toString()
            binding.tvPrice.text = dataItem.currentPrice.toString()

            binding.root.setOnClickListener {
                onItemClick(dataItem)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            QuoteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = getItem(position)
        holder.bind(dataItem, onItemClick)
    }


}