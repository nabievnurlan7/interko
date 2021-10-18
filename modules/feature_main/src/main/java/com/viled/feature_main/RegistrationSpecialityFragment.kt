package com.viled.feature_main

import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.viled.core.common.base.BaseFragment
import com.viled.feature_main.databinding.FragmentRegistrationSpecialityBinding
import com.viled.navigation.NavigationFlow
import com.viled.navigation.ToFlowNavigatable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationSpecialityFragment : BaseFragment(R.layout.fragment_registration_speciality) {

    private val viewModel: MainViewModel by viewModels()
    private val viewBinding: FragmentRegistrationSpecialityBinding by viewBinding()


    override fun setUI() {
        super.setUI()

        val showAdapter = SpinnerAdapter(requireContext(), R.layout.spinner_item)
        showAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        viewBinding.specialitySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }

        viewBinding.specialitySpinner.adapter = showAdapter
        showAdapter.setItems(viewModel.getMockSpinnerData())

        viewBinding.registerButton.setOnClickListener {
            viewModel.setFirstEntry()
            (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.SubjectsFlow)
        }
    }
}