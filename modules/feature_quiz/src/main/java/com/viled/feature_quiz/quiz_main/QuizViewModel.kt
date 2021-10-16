package com.viled.feature_quiz.quiz_main

import androidx.lifecycle.SavedStateHandle
import com.viled.core.common.base.BaseViewModel
import com.viled.core.common.error.ErrorType
import com.viled.core.dto.Mode
import com.viled.core.dto.Question
import com.viled.network.ResponseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: QuizRepository
) : BaseViewModel() {

    sealed class UiState {
        object Loading : UiState()
        class Data(val question: Question, val number: String) : UiState()
        object Finished : UiState()
        class Error(val errorType: ErrorType) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var questions = listOf<Question>()
    private var counter: Int = 0

    private var mode: Mode = savedStateHandle.get<Mode>("mode") ?: Mode()

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        doWorkInMainThread(
            doAsyncBlock = {
                val resultDeferred = async(Dispatchers.IO) {
                    repository.getQuestions(mode.quantity, mode.subjectId)
                }

                val result = resultDeferred.await()
                when (result.status) {
                    ResponseStatus.SUCCESS -> {
                        questions = result.fetchedData!!
                        sendQuestion()
                    }

                    ResponseStatus.ERROR -> _uiState.emit(UiState.Error(result.errorType!!))
                }
            },
            exceptionBlock = {  _uiState.value = UiState.Error(it) }
        )
    }

    private fun sendQuestion() {
        val numberTitle = (counter + 1).toString() + "/" + questions.size.toString()
        _uiState.value = UiState.Data(questions[counter], numberTitle)
    }

    fun giveNextQuestion() {
        if (counter == questions.size - 1) {
            _uiState.value = UiState.Finished
        } else {
            counter++
            sendQuestion()
        }
    }
}