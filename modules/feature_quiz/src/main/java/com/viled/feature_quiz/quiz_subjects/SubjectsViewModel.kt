package com.viled.feature_quiz.quiz_subjects

import com.viled.core.common.base.BaseViewModel
import com.viled.core.common.error.ErrorType
import com.viled.core.dto.Subject
import com.viled.feature_quiz.quiz_main.QuizRepository
import com.viled.network.ResponseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SubjectsViewModel @Inject constructor(
    private val repository: QuizRepository
) : BaseViewModel() {

    sealed class UiState {
        object Loading : UiState()
        class Data(val subjects: List<Subject>) : UiState()
        class Error(val errorType: ErrorType) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadTags()
    }

    private fun loadTags() {
        doWorkInMainThread(
            doAsyncBlock = {
                val resultDeferred = async(Dispatchers.IO) { repository.getTags() }

                val result = resultDeferred.await()
                when (result.status) {
                    ResponseStatus.SUCCESS -> {
                        _uiState.emit(UiState.Data(result.fetchedData!!))
                    }
                    ResponseStatus.ERROR -> _uiState.emit(UiState.Error(result.errorType!!))
                }

            },
            exceptionBlock = { _uiState.value = UiState.Error(it) })
    }
}