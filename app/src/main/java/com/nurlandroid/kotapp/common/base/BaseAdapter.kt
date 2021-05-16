package com.nurlandroid.kotapp.common.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {
    private var items = mutableListOf<T>()
    private var selectedItemPosition: Int? = null

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val isSelected = selectedItemPosition == position
        holder.onBind(items[position], isSelected)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(list: List<T>) {
        if (items != list) {
            items.clear()
            items.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun setSelectedPosition(position: Int) {
        selectedItemPosition = position
        notifyDataSetChanged()
    }

    fun getItem(position: Int) = items[position]

    fun clearAll() {
        items.clear()
        notifyDataSetChanged()
    }
}