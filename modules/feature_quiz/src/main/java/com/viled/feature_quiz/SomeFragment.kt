package com.viled.feature_quiz

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.viled.navigation.NavigationFlow
import com.viled.navigation.ToFlowNavigatable
import kotlinx.android.synthetic.main.fragment_some.*

class SomeFragment : Fragment(R.layout.fragment_some) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        questionsButton.setOnClickListener {
//            (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.QuizFlow)
            findNavController().navigate(R.id.action_someFragment_to_quizFragment)
        }

        profileButton.setOnClickListener {
//            findNavController().navigate(R.id.action_someFragment_to_quizFragment)
                        (requireActivity() as ToFlowNavigatable).navigateToFlow(NavigationFlow.ProfileFlow)

        }
    }
}