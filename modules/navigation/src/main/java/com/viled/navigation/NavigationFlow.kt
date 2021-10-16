package com.viled.navigation

import android.os.Bundle


sealed class NavigationFlow {
    //    object MainFlow : NavigationFlow()
    object QuizFlow : NavigationFlow()
    object JobFlow : NavigationFlow()
    class JobDetailFlow(val bundle: Bundle) : NavigationFlow()
    object ProfileFlow : NavigationFlow()
}