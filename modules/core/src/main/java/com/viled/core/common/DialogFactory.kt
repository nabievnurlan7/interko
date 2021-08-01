package com.viled.core.common

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import com.viled.core.R
import kotlinx.coroutines.FlowPreview


@FlowPreview
object DialogFactory {

    fun getProgressDialog(): ProgressDialog = ProgressDialog()

    fun getErrorDialog(context: Context): Dialog =
        AlertDialog.Builder(context).setCancelable(true).setView(R.layout.dialog_error).create()
}