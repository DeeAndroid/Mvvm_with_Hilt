package com.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weather.data.network.api.response.ListItem
import com.weather.databinding.ItemForecastBinding

class ForecastAdapter() : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    var modelArrayList: ArrayList<ListItem?> = ArrayList<ListItem?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindUI(position, modelArrayList)
    }

    override fun getItemCount(): Int {
        return modelArrayList.size
    }


    fun setModelArrayList(_modelArrayList: List<ListItem?>) {
        modelArrayList.addAll(_modelArrayList)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindUI(
            position: Int,
            _list: ArrayList<ListItem?>
        ) {
            binding.apply {
                binding.day = "Day ${position + 1}"
                binding.temp = (_list[position]?.main?.temp?.minus(273.15))?.toInt().toString()
            }

        }
    }
}