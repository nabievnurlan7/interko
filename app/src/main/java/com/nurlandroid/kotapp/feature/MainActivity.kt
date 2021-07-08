package com.nurlandroid.kotapp.feature

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nurlandroid.kotapp.R
import com.nurlandroid.kotapp.common.extension.setupWithNavController
import com.nurlandroid.kotapp.databinding.ActivityMainBinding
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.activity_main), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private val viewBinding: ActivityMainBinding by viewBinding(R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            initBottomNavigation()
        }

    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {

        val navGraphIds = listOf(
                R.navigation.main_nav
        )

        val controller = viewBinding.bottomNavigation.setupWithNavController(
                navGraphIds = navGraphIds,
                fragmentManager = supportFragmentManager,
                containerId = R.id.fragmentContainer,
                intent = intent
        )

        controller.observe(this, Observer(::onControllerChanged))
    }

    private fun onControllerChanged(data: NavController) {
        data.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.quizFragment -> viewBinding.bottomNavigation.isVisible = true
                else -> viewBinding.bottomNavigation.isVisible = true
            }
        }
    }

    private fun moveToMain() {
        viewBinding.bottomNavigation.selectedItemId = viewBinding.bottomNavigation.menu[0].itemId
    }
}