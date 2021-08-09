package com.viled.feature_quiz

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.viled.core.AppWithFacade
import com.viled.core.common.base.BaseFragment
import com.viled.feature_quiz.QuizViewModel.UiState
import com.viled.feature_quiz.databinding.FragmentQuizBinding
import com.viled.feature_quiz.di.QuizComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class QuizFragment : BaseFragment(R.layout.fragment_quiz) {

    private lateinit var viewModel: QuizViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewBinding: FragmentQuizBinding by viewBinding()
    private lateinit var questionAdapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QuizComponent.create((requireActivity().application as AppWithFacade).getFacade())
            .inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(QuizViewModel::class.java)
    }

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
                        Toast.makeText(requireContext(), it.errorType.message, Toast.LENGTH_LONG)
                            .show()
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
        viewBinding.questionRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = questionAdapter
        }
    }
}