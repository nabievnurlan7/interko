package com.nurlandroid.kotapp.feature.quiz

import android.view.ViewGroup
import com.nurlandroid.kotapp.common.base.BaseAdapter
import com.nurlandroid.kotapp.common.base.BaseViewHolder

class QuestionAdapter(
        private val listener: (Int, Int, Question) -> Unit
) : BaseAdapter<Question>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Question> =
            QuestionViewHolder(parent, listener)
}