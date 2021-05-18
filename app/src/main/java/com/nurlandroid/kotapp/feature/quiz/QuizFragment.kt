package com.nurlandroid.kotapp.feature.quiz

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nurlandroid.kotapp.R
import com.nurlandroid.kotapp.common.base.BaseFragment
import com.nurlandroid.kotapp.feature.quiz.QuizViewModel.UiState
import kotlinx.android.synthetic.main.fragment_quiz.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class QuizFragment : BaseFragment(R.layout.fragment_quiz) {
    private val viewModel: QuizViewModel by viewModel()

    private lateinit var questionAdapter: QuestionAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.uiState.observe(viewLifecycleOwner, {
            when (it) {
                is UiState.Loading -> showProgress()
                is UiState.Data -> {
                    closeProgress()
                }
                is UiState.Error -> {
                    closeProgress()
                    Timber.e("${it.errorType}")
                }
            }
        })

        setRecycler()
    }

    private fun setRecycler() {
        questionAdapter = QuestionAdapter() { _, _, item ->
        }

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        questionRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = questionAdapter
        }
    }

}