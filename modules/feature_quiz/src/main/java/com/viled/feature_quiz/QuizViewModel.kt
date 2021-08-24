package com.viled.feature_quiz

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.viled.core.common.base.BaseViewModel
import com.viled.core.common.error.ErrorType
import com.viled.core.common.network.ResponseStatus
import com.viled.core.dto.Question
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor
(
    private val repository: QuizRepository
) : BaseViewModel() {

    sealed class UiState {
        object Loading : UiState()
        class Data(var questions: List<Question>) : UiState()
        class Error(val errorType: ErrorType) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        val tagId = 0
        doWorkInMainThread(
            doAsyncBlock = {
                val resultDeferred = async(Dispatchers.IO) {
                    repository.getQuestions(20, tagId)
                }

                val result = resultDeferred.await()
                when (result.status) {
                    ResponseStatus.SUCCESS -> {
                        val questions = result.fetchedData!!
                        _uiState.emit(UiState.Data(questions))
                    }
                    ResponseStatus.ERROR -> _uiState.emit(UiState.Error(result.errorType!!))
                }
            },
            exceptionBlock = { Timber.e("$it") }
        )
    }
}