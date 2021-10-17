package com.viled.feature_subjects

import android.view.ViewGroup
import com.viled.core.common.base.BaseAdapter
import com.viled.core.common.base.BaseViewHolder
import com.viled.core.dto.Subject

class SubjectsAdapter(
    private val listener: (Int, Int, Subject) -> Unit
) : BaseAdapter<Subject>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Subject> =
        SubjectViewHolder(parent, listener)
}