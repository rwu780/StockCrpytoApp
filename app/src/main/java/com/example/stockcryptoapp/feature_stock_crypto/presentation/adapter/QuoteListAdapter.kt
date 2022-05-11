package com.example.stockcryptoapp.feature_stock_crypto.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.stockcryptoapp.databinding.QuoteListItemBinding
import com.example.stockcryptoapp.feature_stock_crypto.domain.model.Stock
import com.google.android.material.textview.MaterialTextView


class QuoteListAdapter(
    private val onItemClick: (Stock) -> Unit,
    private val setTextColor: (Stock, MaterialTextView) -> Unit

) : androidx.recyclerview.widget.ListAdapter<Stock, QuoteListAdapter.ViewHolder>(DiffUtilCallback) {

    companion object {
        private val DiffUtilCallback = object : DiffUtil.ItemCallback<Stock>(){
            override fun areItemsTheSame(
                oldItem: Stock,
                newItem: Stock
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Stock,
                newItem: Stock
            ): Boolean {
                return oldItem.ticker == newItem.ticker
            }

        }
    }


    class ViewHolder(private val binding: QuoteListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(dataItem: Stock, onItemClick: (Stock) -> Unit, setTextColor: (Stock, MaterialTextView) -> Unit){
            binding.tvTicker.text = dataItem.ticker
            binding.tvName.text = dataItem.name
            binding.tvChange.text = dataItem.changePercent
            binding.tvPrice.text = dataItem.currentPrice

            setTextColor(dataItem, binding.tvChange)


            binding.tvChange.setOnClickListener {
                if (binding.tvChange.text == dataItem.changePercent){
                    binding.tvChange.text = dataItem.change
                }
                else {
                    binding.tvChange.text = dataItem.changePercent
                }
            }

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
        holder.bind(dataItem, onItemClick, setTextColor)
    }


}