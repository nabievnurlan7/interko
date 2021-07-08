package com.nurlandroid.kotapp.feature.quiz

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nurlandroid.kotapp.R
import com.nurlandroid.kotapp.common.base.BaseFragment
import com.nurlandroid.kotapp.di.dagger.Injectable
import com.nurlandroid.kotapp.feature.quiz.QuizViewModel.UiState
import kotlinx.android.synthetic.main.fragment_quiz.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuizFragment : BaseFragment(R.layout.fragment_quiz), Injectable {
    //private val viewModel: QuizViewModel by viewModel()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: QuizViewModel by viewModels { viewModelFactory }

    private lateinit var questionAdapter: QuestionAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.uiState.collect {
                when (it) {
                    is UiState.Loading -> showProgress()
                    is UiState.Data -> {
                        questionAdapter.setItems(it.questions)
                        closeProgress()
                    }
                    is UiState.Error -> {
                        closeProgress()
                        Toast.makeText(requireContext(), it.errorType.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        setRecycler()
    }

    private fun setRecycler() {
        questionAdapter = QuestionAdapter { _, _, item ->
        }

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        questionRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = questionAdapter
        }
    }
}