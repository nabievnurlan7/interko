package com.viled.feature_main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckedTextView

class SpinnerAdapter(
    context: Context,
    itemViewResId: Int
) : ArrayAdapter<SpinnerItem>(context, itemViewResId) {

    private var items = mutableListOf(SpinnerItem(0, context.getString(R.string.loading)))

    var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View =
        getCustomView(position, convertView, parent)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
        getCustomView(position, convertView, parent)

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView: View = inflater.inflate(R.layout.spinner_item, parent, false)
        val label = itemView.findViewById(R.id.spinnerItem) as CheckedTextView
        label.text = items[position].label
        return itemView
    }

    fun setItems(list: List<SpinnerItem>) {
        clear()
        items.clear()
        items.addAll(list)
        addAll(list)
        notifyDataSetChanged()
    }
}

data class SpinnerItem(val itemId: Int, val label: String)