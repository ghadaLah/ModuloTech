package com.test.modulotech.base

import androidx.lifecycle.ViewModel
import com.test.modulotech.di.DaggerAppComponent
import com.test.modulotech.di.NetworkModule
import com.test.modulotech.ui.viewmodel.HomeViewModel

abstract class BaseViewModel : ViewModel(){
    val injector = DaggerAppComponent
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when(this) {
            is HomeViewModel -> injector.inject(this)
        }
    }
}