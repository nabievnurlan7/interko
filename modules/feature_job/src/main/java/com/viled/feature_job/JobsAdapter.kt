package com.viled.feature_job

import android.view.ViewGroup
import com.viled.core.common.base.BaseAdapter
import com.viled.core.common.base.BaseViewHolder
import com.viled.core.dto.Job

class JobsAdapter(
    private val listener: (Int, Int, Job) -> Unit
) : BaseAdapter<Job>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Job> =
        JobViewHolder(parent, listener)
}