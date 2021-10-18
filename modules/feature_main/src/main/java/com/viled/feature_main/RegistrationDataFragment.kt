package com.viled.feature_main

import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.viled.core.common.base.BaseFragment
import com.viled.feature_main.databinding.FragmentRegistrationDataBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegistrationDataFragment : BaseFragment(R.layout.fragment_registration_data) {

    private val viewBinding: FragmentRegistrationDataBinding by viewBinding()

    override fun setUI() {
        super.setUI()

        viewBinding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_registrationDataFragment_to_registrationSpecialityFragment)
        }
    }
}