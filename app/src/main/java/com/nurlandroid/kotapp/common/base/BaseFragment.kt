package com.nurlandroid.kotapp.common.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.nurlandroid.kotapp.common.DialogFactory
import com.nurlandroid.kotapp.common.ProgressDialog
import com.nurlandroid.kotapp.common.error.ErrorType

abstract class BaseFragment(private val layout: Int) : Fragment(layout) {


    private lateinit var progressDialog: ProgressDialog
    private lateinit var errorDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = DialogFactory.getProgressDialog()
        errorDialog = DialogFactory.getErrorDialog(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
            inflater.inflate(layout, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
    }

    protected open fun setUI() {
        closeProgress()
        closeError()
    }

    protected open fun showProgress() {
        if (!progressDialog.isResumed) {
            progressDialog.show(childFragmentManager, "")
        }
    }

    protected fun closeProgress() {
        if (progressDialog.isResumed) {
            progressDialog.dismiss()
        }
    }

    protected open fun showError(errorType: ErrorType) {
        errorType.message

        if (!errorDialog.isShowing) {
            closeProgress()
            errorDialog.show()
        }
    }

    protected open fun closeError() {
        if (!errorDialog.isShowing) {
            errorDialog.dismiss()
        }
    }

    open fun initMoveBackDispatcher() {
        initBackDispatcher { findNavController().popBackStack() }
    }

    open fun initMoveToMainDispatcher() {
    }

    private fun initBackDispatcher(block: () -> Unit) {
        requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        block.invoke()
                    }
                }
        )
    }
}