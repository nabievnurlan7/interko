package com.viled.feature_main

import android.os.Bundle
import android.view.View
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.viled.core.common.base.BaseFragment
import com.viled.feature_main.MainViewModel.Companion.OPEN_BIOMETRY
import com.viled.feature_main.MainViewModel.Companion.OPEN_NEXT_FLOW
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
            .setTitle(getString(R.string.bio_title))
            .setSubtitle(getString(R.string.bio_subtitle))
            .setNegativeButtonText(getString(R.string.bio_cancel))
            .build()

        val canAuthenticate =
            BiometricManager.from(requireActivity().applicationContext).canAuthenticate()

        viewBinding.biometryCheckBox.isVisible =
            canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS

        viewBinding.loginButton.setOnClickListener {
            viewModel.login("", "", viewBinding.biometryCheckBox.isChecked)
        }

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.uiState.collect { processViewState(it) }
        }
    }

    private fun processViewState(state: UiState) {
        when (state) {
            is UiState.Idle -> { }
            is UiState.Loading -> showProgress()
            is UiState.Data -> {
                closeProgress()
                if (state.successStatus == OPEN_NEXT_FLOW) {
                    (requireActivity() as ToFlowNavigatable).navigateToFlow(
                        NavigationFlow.SubjectsFlow
                    )
                } else if (state.successStatus == OPEN_BIOMETRY) {
                    viewBinding.biometryCheckBox.isChecked = true
                    biometricPrompt.authenticate(promptInfo)
                }
            }
            is UiState.Error -> closeProgress()
        }
    }
}