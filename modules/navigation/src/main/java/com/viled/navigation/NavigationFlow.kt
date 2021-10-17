package com.viled.navigation

import android.os.Bundle


sealed class NavigationFlow {
    object SubjectsFlow : NavigationFlow()
    class QuizFlow(val bundle: Bundle) : NavigationFlow()
    object JobFlow : NavigationFlow()
    class JobDetailFlow(val bundle: Bundle) : NavigationFlow()
    object ProfileFlow : NavigationFlow()
}