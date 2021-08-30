package com.viled.feature_profile

import by.kirich1409.viewbindingdelegate.viewBinding
import com.viled.core.common.base.BaseFragment
import com.viled.feature_profile.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    private val viewBinding: FragmentSettingsBinding by viewBinding()

    override fun setUI() {
        super.setUI()
        with(viewBinding) {
           // toolbar.titleTextView.text = getString(R.string.settings)
        }
    }
}