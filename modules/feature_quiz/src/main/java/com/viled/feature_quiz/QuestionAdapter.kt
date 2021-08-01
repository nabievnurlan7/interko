package com.viled.feature_quiz

import android.view.ViewGroup
import com.viled.core.common.base.BaseAdapter
import com.viled.core.common.base.BaseViewHolder
import com.viled.core.dto.Question

class QuestionAdapter(
    private val listener: (Int, Int, Question) -> Unit
) : BaseAdapter<Question>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Question> =
        QuestionViewHolder(parent, listener)
}