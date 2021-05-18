package com.nurlandroid.kotapp.common

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.nurlandroid.kotapp.feature.quiz.QuizFragment

class CustomFragmentFactory : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment = when (className) {
        QuizFragment::class.java.name -> QuizFragment()
        else -> super.instantiate(classLoader, className)
    }
}