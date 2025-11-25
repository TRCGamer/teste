package com.example.registroponto.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.registroponto.R
import com.example.registroponto.data.Registro
import com.example.registroponto.data.TipoRegistro

class RegistroAdapter : ListAdapter<Registro, RegistroAdapter.RegistroViewHolder>(RegistroDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_registro, parent, false)
        return RegistroViewHolder(view)
    }

    override fun onBindViewHolder(holder: RegistroViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class RegistroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTipo: TextView = itemView.findViewById(R.id.tvTipo)
        private val tvDataHora: TextView = itemView.findViewById(R.id.tvDataHora)
        private val tvLocalizacao: TextView = itemView.findViewById(R.id.tvLocalizacao)

        fun bind(registro: Registro) {
            tvTipo.text = registro.tipo.name
            tvDataHora.text = registro.getDataHoraFormatada()
            tvLocalizacao.text = registro.getLocalizacaoFormatada()

            // Colorir tipo
            val color = if (registro.tipo == TipoRegistro.ENTRADA) {
                android.graphics.Color.parseColor("#4CAF50") // Verde
            } else {
                android.graphics.Color.parseColor("#F44336") // Vermelho
            }
            tvTipo.setTextColor(color)
        }
    }

    class RegistroDiffCallback : DiffUtil.ItemCallback<Registro>() {
        override fun areItemsTheSame(oldItem: Registro, newItem: Registro): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Registro, newItem: Registro): Boolean {
            return oldItem == newItem
        }
    }
}
