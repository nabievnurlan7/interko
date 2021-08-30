package com.viled.feature_job

import android.view.ViewGroup
import com.viled.core.common.base.BaseViewHolder
import com.viled.feature_job.databinding.ItemJobBinding


class JobViewHolder(
    parent: ViewGroup,
    listener: (Int, Int, Job) -> Unit
) : BaseViewHolder<Job>(R.layout.item_job, parent, listener) {
    private val binding: ItemJobBinding = ItemJobBinding.bind(itemView)

    override fun onBind(item: Job, selected: Boolean) {
        super.onBind(item, selected)

        with(binding) {
            companyNameTextView.text = item.company.name
            descriptionTextView.text = item.positionName
            salaryTextView.text = item.salary
            locationTextView.text = item.location
        }
    }
}