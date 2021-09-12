package com.viled.feature_main

import com.viled.core.common.SharedPrefLayer
import com.viled.core.common.base.BaseViewModel
import com.viled.core.common.crypto.CryptoUtils
import com.viled.core.common.error.ErrorType
import com.viled.network.ResponseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
    private val cryptoUtils: CryptoUtils,
    private val sharedPrefLayer: SharedPrefLayer
) : BaseViewModel() {

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        class Data(var successStatus: String) : UiState()
        class Error(val errorType: ErrorType) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    init {
        if (sharedPrefLayer.token.isNotEmpty() && sharedPrefLayer.isBiometry) {
            _uiState.value = UiState.Data(OPEN_BIOMETRY)
        }
    }

    fun login(phone: String, password: String, checked: Boolean) {

        doWorkInMainThread(
            doAsyncBlock = {
                val resultDeferred = async(Dispatchers.IO) {
                    repository.login(phone, password)
                }

                val result = resultDeferred.await()
                when (result.status) {
                    ResponseStatus.SUCCESS -> {
                        val token = result.fetchedData!!
                        encrypt(token)
                        sharedPrefLayer.isBiometry = checked
                    }
                    ResponseStatus.ERROR -> _uiState.emit(UiState.Error(result.errorType!!))
                }
            },
            exceptionBlock = {
                Timber.e("$it")
            }
        )
    }

    private fun encrypt(token: String) {
        val encryptedValue = cryptoUtils.encrypt(token)
        sharedPrefLayer.token = encryptedValue
        _uiState.value = UiState.Data(OPEN_NEXT_FLOW)
    }

    fun setSuccess() {
        _uiState.value = UiState.Data(OPEN_NEXT_FLOW)
    }

    companion object {
        const val OPEN_NEXT_FLOW = "go"
        const val OPEN_BIOMETRY = "show_bio"
    }
}