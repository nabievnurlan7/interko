package com.viled.navigation

sealed class NavigationFlow {
    object QuizFlow : NavigationFlow()
    object SomeFlow : NavigationFlow()
}