package com.nurlandroid.kotapp.feature.quiz

import androidx.lifecycle.LiveData
import com.nurlandroid.kotapp.common.base.BaseViewModel


class QuizViewModel(private val repository: QuizRepository) : BaseViewModel() {

    sealed class InterviewState {
        object Loading : InterviewState()
        class NextQuestion(val question: Question, val number: String) : InterviewState()
        class Error(val throwable: Throwable) : InterviewState()
    }

    private var mutableState = SingleLiveEvent<InterviewState>()
    val uiState: LiveData<InterviewState>
        get() = mutableState

    private var questions = listOf<Question>()
    private var counter: Int = 0

    fun loadQuestions(mode: Int, tagId: Int) {
        doWorkInMainThread {
            mutableState.postValue(InterviewState.Loading)
            repository.getQuestions(mode, tagId).loadedData?.let {
                questions = it
                sendQuestion()
            }
        }
    }

}