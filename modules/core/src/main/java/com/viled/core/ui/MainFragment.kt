package com.viled.core.ui

import android.os.Bundle
import android.view.View
import com.viled.core.R
import com.viled.core.common.base.BaseFragment
import com.viled.navigation.NavigationFlow
import com.viled.navigation.ToFlowNavigatable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.SomeFlow)
    }
}