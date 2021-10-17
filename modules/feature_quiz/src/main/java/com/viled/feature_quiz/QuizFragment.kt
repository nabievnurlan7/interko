package com.viled.feature_quiz

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.viled.core.common.INTERVIEW_MODE
import com.viled.core.common.base.BaseFragment
import com.viled.core.dto.Mode
import com.viled.core.dto.Question
import com.viled.feature_quiz.QuizViewModel.UiState
import com.viled.feature_quiz.databinding.FragmentQuizBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*


@AndroidEntryPoint
class QuizFragment : BaseFragment(R.layout.fragment_quiz), TextToSpeech.OnInitListener,
    LifecycleOwner {

    private val viewModel: QuizViewModel by viewModels()
    private val viewBinding: FragmentQuizBinding by viewBinding()
    private val args by navArgs<QuizFragmentArgs>()
    private lateinit var textToSpeech: TextToSpeech
    private var cameraLayer: CameraLayer? = null
    private lateinit var mode: Mode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<Mode>(INTERVIEW_MODE)?.let { mode = it }
    }

    override fun onResume() {
        super.onResume()
        if (mode.isRealInterview) {
            cameraLayer = CameraLayer(requireActivity(), viewBinding.cameraView)
            cameraLayer?.setUpCamera()
        }
    }

    override fun onStop() {
        super.onStop()
        if (mode.isRealInterview) {
            cameraLayer?.releaseCamera()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.uiState.collect {
                when (it) {
                    is UiState.Loading -> {
                        showProgress()
                        if (mode.isRealInterview) {
                            cameraLayer?.startRecording()
                        }
                    }
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
                        if (mode.isRealInterview) {
                            cameraLayer?.stopRecording()
                            Toast.makeText(
                                requireContext(),
                                "Interview is finished. Thank you!",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        moveToSubjects()
                    }
                }
            }
        }
    }

    override fun setUI() {
        super.setUI()
        textToSpeech = TextToSpeech(requireContext(), this)

        with(viewBinding) {
            nextButton.setOnClickListener { viewModel.giveNextQuestion() }
        }
    }

    private fun setQuestion(question: Question, numberTitle: String) {
        with(viewBinding) {
            questionTextView.text = question.question
            questionNumberTextView.text = numberTitle
        }

        if (mode.isRealInterview) {
            speakOut(question.question)
        }
    }

    private fun speakOut(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // val locale = Locale("ru")
            val locale = Locale.getDefault()
            val result: Int = textToSpeech.setLanguage(locale)
            if (result == TextToSpeech.LANG_MISSING_DATA
                || result == TextToSpeech.LANG_NOT_SUPPORTED
            ) {
                Timber.e("This Language is not supported")
            }
        } else {
            Timber.e("TTS Initialization Failed!")
        }
    }

    private fun moveToSubjects() {
        findNavController().navigate(R.id.action_global_subjects)
    }

    override fun onDestroy() {
        if (mode.isRealInterview) {
            textToSpeech.stop()
            textToSpeech.shutdown()
            cameraLayer = null
        }
        super.onDestroy()
    }
}