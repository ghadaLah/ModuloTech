package com.test.modulotech.di

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.test.modulotech.model.AppDatabase
import com.test.modulotech.ui.viewmodel.HomeViewModel
import dagger.Module
import javax.inject.Provider

class ViewModelFactory( private val activity: AppCompatActivity): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "posts").build()
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(db.devicesDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}