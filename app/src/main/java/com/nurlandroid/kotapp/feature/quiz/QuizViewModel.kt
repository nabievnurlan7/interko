package com.nurlandroid.kotapp.feature.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nurlandroid.kotapp.common.base.BaseViewModel
import com.nurlandroid.kotapp.common.error.ErrorType
import com.nurlandroid.kotapp.common.network.ResponseStatus
import kotlinx.coroutines.async


class QuizViewModel(private val repository: QuizRepository) : BaseViewModel() {

    sealed class UiState {
        object Loading : UiState()
        class Data(var questions: List<Question>) : UiState()
        class Error(val errorType: ErrorType) : UiState()
    }


}