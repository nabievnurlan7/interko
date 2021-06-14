package com.nurlandroid.kotapp.feature.quiz

import androidx.lifecycle.viewModelScope
import com.nurlandroid.kotapp.common.base.BaseViewModel
import com.nurlandroid.kotapp.common.error.ErrorType
import com.nurlandroid.kotapp.common.network.ResponseStatus
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class QuizViewModel(private val repository: QuizRepository) : BaseViewModel() {

    sealed class UiState {
        object Loading : UiState()
        class Data(var questions: List<Question>) : UiState()
        class Error(val errorType: ErrorType) : UiState()
    }

//    private var mutableState = MutableLiveData<UiState>()
//    val liveData: LiveData<UiState>
//        get() = mutableState

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        val tagId = 0
        doWorkInMainThread(
                doAsyncBlock = {
                    val resultDeferred = async {
                        repository.getQuestions(20, tagId)
                    }

                    // For HomeTask#2
                    if (tagId == 1) {
                        throw IllegalArgumentException("!!!")
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
                exceptionBlock = {
                    viewModelScope.launch { _uiState.emit(UiState.Error(it)) }
                }
        )
    }
}