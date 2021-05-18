package com.nurlandroid.kotapp.feature.quiz

import android.view.ViewGroup
import com.nurlandroid.kotapp.R
import com.nurlandroid.kotapp.common.base.BaseViewHolder
import com.nurlandroid.kotapp.databinding.ItemQuestionBinding

class QuestionViewHolder(
        parent: ViewGroup,
        listener: (Int, Int, Question) -> Unit
) : BaseViewHolder<Question>(R.layout.item_question, parent, listener) {

    private val binding: ItemQuestionBinding = ItemQuestionBinding.bind(itemView)

    override fun onBind(item: Question, selected: Boolean) {
        super.onBind(item, selected)

        with(binding) {
            titleTextView.text = item.question
        }
    }
}