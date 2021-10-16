package com.viled.feature_quiz

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.viled.core.common.base.BaseFragment
import com.viled.core.dto.Question
import com.viled.feature_quiz.QuizViewModel.UiState
import com.viled.feature_quiz.databinding.FragmentQuizBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class QuizFragment : BaseFragment(R.layout.fragment_quiz) {

    private val viewModel: QuizViewModel by viewModels()
    private val viewBinding: FragmentQuizBinding by viewBinding()
    private val args by navArgs<QuizFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.uiState.collect {
                when (it) {
                    is UiState.Loading -> showProgress()
                    is UiState.Data -> {
                        setQuestion(it.question, it.number)
                        closeProgress()
                    }
                    is UiState.Error -> {
                        closeProgress()
                        Toast.makeText(requireContext(), it.errorType.message, Toast.LENGTH_LONG)
                            .show()
                    }
                    UiState.Finished -> {
//                        if (mode.isRealInterview) {
//                            cameraLayer?.stopRecording()
//                            Toast.makeText(
//                                requireContext(),
//                                "Interview is finished. Thank you!",
//                                Toast.LENGTH_LONG
//                            ).show()
//                        }

                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    override fun setUI() {
        super.setUI()

        with(viewBinding) {
            nextButton.setOnClickListener { viewModel.giveNextQuestion() }
        }
    }

    private fun setQuestion(question: Question, numberTitle: String) {
        with(viewBinding) {
            questionTextView.text = question.question
            questionNumberTextView.text = numberTitle
        }

//        if (mode.isRealInterview) {
//            speakOut(question.question)
//        }
    }
}