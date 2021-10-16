package com.viled.feature_quiz.quiz_subjects

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.dialogViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.viled.core.common.LONG_INTERVIEW
import com.viled.core.common.MEDIUM_INTERVIEW
import com.viled.core.common.SHORT_INTERVIEW
import com.viled.feature_quiz.R
import com.viled.feature_quiz.databinding.DialogBottomBinding


class BottomDialog(val listener: (Int) -> Unit) : BottomSheetDialogFragment() {

    private val viewBinding: DialogBottomBinding by dialogViewBinding(R.id.bottomContainer)

    private var chosenQuantity: Int = SHORT_INTERVIEW

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.dismissWithAnimation = true

        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.dialog_bottom, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewBinding) {
            closeButton.setOnClickListener {
                dismiss()
            }

            shortButton.setOnClickListener {
                chosenQuantity = SHORT_INTERVIEW
                dismiss()
            }

            mediumButton.setOnClickListener {
                chosenQuantity = MEDIUM_INTERVIEW
                dismiss()
            }

            longButton.setOnClickListener {
                chosenQuantity = LONG_INTERVIEW
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        listener.invoke(chosenQuantity)
        super.onDestroyView()
    }
}