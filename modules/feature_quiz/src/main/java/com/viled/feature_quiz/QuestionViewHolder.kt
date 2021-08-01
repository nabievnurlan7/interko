package com.viled.feature_quiz

import android.view.ViewGroup
import com.viled.core.common.base.BaseViewHolder
import com.viled.core.dto.Question
import com.viled.feature_quiz.databinding.ItemQuestionBinding

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