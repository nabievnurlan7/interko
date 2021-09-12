package com.viled.feature_main

import android.util.Log
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
class MainViewModel @Inject constructor(private val repository: MainRepository) : BaseViewModel() {

    private lateinit var cryptoUtils: CryptoUtils
    private lateinit var sharedPrefLayer: SharedPrefLayer

    sealed class UiState {
        object Idle : UiState()
        object Loading : UiState()
        class Data(var successStatus: String) : UiState()
        class Error(val errorType: ErrorType) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun setCryptoUtils(cryptoUtils: CryptoUtils) {
        this.cryptoUtils = cryptoUtils
    }

    fun setShared(sharedPrefLayer: SharedPrefLayer) {
        this.sharedPrefLayer = sharedPrefLayer

        if (sharedPrefLayer.token.isNotEmpty() && sharedPrefLayer.isBiometry) {
            _uiState.value = UiState.Data("show_bio")
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
                Log.e("ERROR=", it.toString())
                Timber.e("$it")
            }
        )
    }

    private fun encrypt(token: String) {
        val encryptedValue = cryptoUtils.encrypt(token)
        sharedPrefLayer.token = encryptedValue
        _uiState.value = UiState.Data("go")
    }

    fun setSuccess() {
        _uiState.value = UiState.Data("go")
    }
}