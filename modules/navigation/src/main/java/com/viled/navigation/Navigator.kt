package com.viled.navigation

import androidx.navigation.NavController

class Navigator {

    lateinit var navController: NavController

    fun navigateToFlow(navigationFlow: NavigationFlow) =
        when (navigationFlow) {
            NavigationFlow.QuizFlow -> navController.navigate(R.id.action_global_quiz)
            NavigationFlow.SomeFlow -> navController.navigate(R.id.action_global_quiz)
        }
}