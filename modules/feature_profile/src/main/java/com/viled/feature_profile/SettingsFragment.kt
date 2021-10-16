package com.viled.feature_profile

import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.viled.core.common.base.BaseFragment
import com.viled.feature_profile.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    private val viewBinding: FragmentSettingsBinding by viewBinding()

    override fun setUI() {
        super.setUI()
        with(viewBinding) {
            commonToolbar.setTitle(R.string.settings)
            commonToolbar.setBackClickListener { findNavController().popBackStack() }
        }
    }
}