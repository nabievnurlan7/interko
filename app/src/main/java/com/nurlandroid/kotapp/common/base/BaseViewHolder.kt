package com.nurlandroid.kotapp.common.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(
        @LayoutRes layoutId: Int,
        parent: ViewGroup,
        private val listener: ((Int, Int, T) -> Unit)?
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false)) {

    open fun onBind(item: T, selected: Boolean) {
        itemView.setOnClickListener { listener?.invoke(adapterPosition, it.id, item) }
    }
}