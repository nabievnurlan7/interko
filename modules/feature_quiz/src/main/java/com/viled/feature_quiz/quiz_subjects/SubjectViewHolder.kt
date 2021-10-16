package com.viled.feature_quiz.quiz_subjects

import android.view.ViewGroup
import com.viled.core.common.base.BaseViewHolder
import com.viled.core.dto.Subject
import com.viled.feature_quiz.R
import com.viled.feature_quiz.databinding.ItemSubjectBinding


class SubjectViewHolder(
    parent: ViewGroup,
    listener: (Int, Int, Subject) -> Unit
) : BaseViewHolder<Subject>(R.layout.item_subject, parent, listener) {

    private val binding: ItemSubjectBinding = ItemSubjectBinding.bind(itemView)

    override fun onBind(item: Subject, selected: Boolean) {
        super.onBind(item, selected)

        with(binding) {
            subjectTextView.text = item.tagName
        }
    }
}