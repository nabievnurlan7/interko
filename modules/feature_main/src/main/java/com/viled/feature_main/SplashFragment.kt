package com.viled.feature_main

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.viled.core.common.base.BaseFragment
import com.viled.feature_main.databinding.FragmentSplashBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private val viewBinding: FragmentSplashBinding by viewBinding()

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

            findNavController()
                .navigate(
                    R.id.action_splashFragment_to_loginFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.splashFragment, true)
                        .build()
                )
//            val scope = CoroutineScope(Dispatchers.Main + Job())
//            scope.launch {
//
//            }
        }
    }

}