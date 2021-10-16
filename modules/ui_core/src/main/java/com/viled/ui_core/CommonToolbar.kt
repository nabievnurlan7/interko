package com.viled.ui_core

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class CommonToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var toolbar: ConstraintLayout
    private var backImageView: ImageView
    private var titleTextView: TextView
    private var bottomLine: View

    private var backClickedLambda: (() -> Unit)? = null
    private var searchClickedLambda: (() -> Unit)? = null

    private var titleText: String = ""
    private var isBottomLineVisible: Boolean = true
    private var isAlphaBackground: Boolean = false

    init {
        View.inflate(context, R.layout.common_toolbar, this)

        toolbar = findViewById(R.id.toolbarContainer)
        backImageView = findViewById(R.id.backImageView)
        titleTextView = findViewById(R.id.titleTextView)

        bottomLine = findViewById(R.id.bottomLine)

        backImageView.setOnClickListener { backClickedLambda?.invoke() }
        bottomLine.isVisible = isBottomLineVisible

        if (isAlphaBackground) {
            toolbar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.whiteBlurred))
        }
    }

    fun setBackClickListener(block: () -> Unit) {
        backClickedLambda = block
        backImageView.isVisible = true
    }

    fun setSearchClickListener(block: () -> Unit) {
        searchClickedLambda = block
    }

    fun setTitle(titleTextId: Int) {
        titleTextView.isVisible = true
        titleText = context.getString(titleTextId)
        titleTextView.text = titleText
    }

    fun setTitle(text: String) {
        titleTextView.isVisible = true
        titleText = text
        titleTextView.text = titleText
    }
}