package com.nurlandroid.kotapp.common

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Paginator(
        recyclerView: RecyclerView,
        private var isLoading: () -> Boolean,
        private val loadMore: (Int) -> Unit,
        private val onLast: () -> Boolean = { true }
) : RecyclerView.OnScrollListener() {

    private var threshold: Int = 2
    private var currentPage: Int = 1
    private var endWithAuto: Boolean = false

    init {
        recyclerView.addOnScrollListener(this)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager

        layoutManager?.let {
            val visibleItemCount = it.childCount
            val totalItemCount = it.itemCount
            val firstVisibleItemPosition = when (it) {
                is GridLayoutManager -> it.findLastVisibleItemPosition()
                is LinearLayoutManager -> it.findLastVisibleItemPosition()
                else -> return
            }

            if (onLast() || isLoading()) return

            if (endWithAuto) {
                if (visibleItemCount + firstVisibleItemPosition == totalItemCount) return
            }

            if ((visibleItemCount + firstVisibleItemPosition + threshold) >= totalItemCount) {
                loadMore(++currentPage)
                isLoading = { true }
            }
        }
    }
}