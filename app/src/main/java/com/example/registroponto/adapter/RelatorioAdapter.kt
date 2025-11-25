package com.example.registroponto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.registroponto.RelatorioActivity
import com.example.registroponto.databinding.ItemRelatorioBinding

class RelatorioAdapter : ListAdapter<RelatorioActivity.ItemRelatorio, RelatorioAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRelatorioBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemRelatorioBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RelatorioActivity.ItemRelatorio) {
            binding.tvData.text = item.data
            binding.tvHoras.text = item.horasTrabalhadas
            binding.tvDetalhes.text = item.detalhes
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<RelatorioActivity.ItemRelatorio>() {
        override fun areItemsTheSame(
            oldItem: RelatorioActivity.ItemRelatorio,
            newItem: RelatorioActivity.ItemRelatorio
        ): Boolean {
            return oldItem.data == newItem.data
        }

        override fun areContentsTheSame(
            oldItem: RelatorioActivity.ItemRelatorio,
            newItem: RelatorioActivity.ItemRelatorio
        ): Boolean {
            return oldItem == newItem
        }
    }
}
