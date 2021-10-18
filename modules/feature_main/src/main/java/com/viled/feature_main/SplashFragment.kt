package com.viled.feature_main

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.viled.core.common.base.BaseFragment
import com.viled.feature_main.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private val viewModel: MainViewModel by viewModels()
    private val viewBinding: FragmentSplashBinding by viewBinding()

    private val navOptions = NavOptions.Builder()
        .setPopUpTo(R.id.splashFragment, true)
        .build()

    override fun setUI() {
        super.setUI()
        initMoveBackDispatcher()

        val pulseAnimation: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            viewBinding.logoImageView,
            PropertyValuesHolder.ofFloat("scaleX", 1.2f),
            PropertyValuesHolder.ofFloat("scaleY", 1.2f)
        )
        pulseAnimation.duration = 800
        pulseAnimation.repeatCount = ObjectAnimator.INFINITE
        pulseAnimation.repeatMode = ObjectAnimator.REVERSE
        pulseAnimation.start()

        MainScope().launch {
            delay(4000L)

            if (viewModel.isFirstEntry()) {
                moveToRegistration()
            } else {
                moveToLogin()
            }
        }
    }

    private fun moveToLogin() {
        findNavController()
            .navigate(R.id.action_splashFragment_to_loginFragment, null, navOptions)
    }

    private fun moveToRegistration() {
        findNavController()
            .navigate(R.id.action_splashFragment_to_registrationDataFragment, null, navOptions)
    }
}