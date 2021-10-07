package com.viled.feature_quiz

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.viled.core.common.SHORT_INTERVIEW
import com.viled.core.dto.Mode
import com.viled.navigation.NavigationFlow
import com.viled.navigation.ToFlowNavigatable
import kotlinx.android.synthetic.main.fragment_some.*

class SomeFragment : Fragment(R.layout.fragment_some) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionsButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_someFragment_to_quizFragment,
                bundleOf("mode" to Mode(true, SHORT_INTERVIEW, 1))
            )
        }

        profileButton.setOnClickListener {
            (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.ProfileFlow)
        }
    }
}