package com.viled.navigation

import androidx.navigation.NavController

class Navigator {

    lateinit var navController: NavController

    fun navigateToFlow(navigationFlow: NavigationFlow) =
        when (navigationFlow) {
//            NavigationFlow.MainFlow -> navController.navigate(R.id.action_global_quiz)

            is NavigationFlow.SubjectsFlow -> navController.navigate(R.id.action_global_subjects)
            is NavigationFlow.QuizFlow -> navController.navigate(
                R.id.action_global_quiz,
                navigationFlow.bundle
            )
            is NavigationFlow.JobFlow -> navController.navigate(R.id.action_global_job)
            is NavigationFlow.JobDetailFlow -> navController.navigate(
                R.id.action_global_job_detail,
                navigationFlow.bundle
            )
            is NavigationFlow.ProfileFlow -> navController.navigate(R.id.action_global_profile)
        }
}