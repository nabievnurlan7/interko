package com.viled.navigation

sealed class NavigationFlow {
    object QuizFlow : NavigationFlow()
    object JobFlow : NavigationFlow()
    object ProfileFlow : NavigationFlow()
}