package com.test.modulotech.ui

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.navigation.findNavController
import com.test.modulotech.base.BaseFragment
import com.test.modulotech.*

class SplashScreenFragment: BaseFragment() {

    override val resLayout: Int = R.layout.splach_screen_fragment

    companion object {
        private const val SPLACH_SCREEN_DISPLAY = 3000L
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Handler().postDelayed({
            view.findNavController().navigate(R.id.homeFragment)
        }, SPLACH_SCREEN_DISPLAY)
    }
}