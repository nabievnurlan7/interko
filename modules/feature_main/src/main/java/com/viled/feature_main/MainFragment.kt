package com.viled.feature_main

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.viled.core.common.SharedPrefLayer
import com.viled.core.common.base.BaseFragment
import com.viled.core.common.crypto.CryptoUtils
import com.viled.core.common.crypto.CryptoUtilsLowerMReified
import com.viled.core.common.crypto.CryptoUtilsReified
import com.viled.feature_main.MainViewModel.UiState
import com.viled.feature_main.databinding.FragmentMainBinding
import com.viled.navigation.NavigationFlow
import com.viled.navigation.ToFlowNavigatable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()
    private val viewBinding: FragmentMainBinding by viewBinding()
    private lateinit var cryptoUtils: CryptoUtils

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    viewModel.setSuccess()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        cryptoUtils =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) CryptoUtilsReified()
            else CryptoUtilsLowerMReified(requireContext())

        viewModel.setCryptoUtils(cryptoUtils)
        viewModel.setShared(SharedPrefLayer(requireContext()))

        val canAuthenticate =
            BiometricManager.from(requireActivity().applicationContext).canAuthenticate()

        viewBinding.biometryCheckBox.isVisible =
            canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS

        viewBinding.loginButton.setOnClickListener {
            viewModel.login("", "", viewBinding.biometryCheckBox.isChecked)
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.uiState.collect {
                when (it) {
                    is UiState.Loading -> showProgress()
                    is UiState.Idle -> {
                    }
                    is UiState.Data -> {
                        closeProgress()
                        if (it.successStatus == "go") {
                            (requireActivity() as ToFlowNavigatable).navigateToFlow(
                                NavigationFlow.QuizFlow
                            )
                        } else if (it.successStatus == "show_bio") {
                            viewBinding.biometryCheckBox.isChecked = true
                            biometricPrompt.authenticate(promptInfo)
                        }
                    }
                    is UiState.Error -> {
                        closeProgress()
                    }
                }
            }
        }
    }
}