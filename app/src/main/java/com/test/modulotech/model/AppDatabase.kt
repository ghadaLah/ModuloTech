package com.test.modulotech.model

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.modulotech.utils.Converter

@Database(entities = [DeviceData.LightModel::class, DeviceData.HeaterModel::class, DeviceData.RollerShutterModel::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun devicesDao(): DevicesDao
}