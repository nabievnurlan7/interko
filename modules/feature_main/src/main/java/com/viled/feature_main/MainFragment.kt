package com.viled.feature_main

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.viled.core.common.SharedPrefLayer
import com.viled.core.common.base.BaseFragment
import com.viled.core.common.crypto.CryptoUtils
import com.viled.core.common.crypto.CryptoUtilsLowerMReified
import com.viled.core.common.crypto.CryptoUtilsReified
import com.viled.feature_main.databinding.FragmentMainBinding
import com.viled.navigation.NavigationFlow
import com.viled.navigation.ToFlowNavigatable
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()
    private val viewBinding: FragmentMainBinding by viewBinding()
    private lateinit var cryptoUtils: CryptoUtils

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cryptoUtils =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) CryptoUtilsReified()
            else CryptoUtilsLowerMReified(requireContext())

        viewModel.setCryptoUtils(cryptoUtils)
        viewModel.setShared(SharedPrefLayer(requireContext()))

        viewBinding.loginButton.setOnClickListener {
            viewModel.login("", "")
           // (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.QuizFlow)
        }
    }
}