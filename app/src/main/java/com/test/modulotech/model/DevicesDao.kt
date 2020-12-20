package com.test.modulotech.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DevicesDao {
    @get:Query("SELECT * FROM DeviceData")
    val deviceList: List<DeviceData>

    @get:Query("SELECT * FROM ResponseModel")
    val data: ResponseModel

    @Insert
    fun insertAll(data: ResponseModel)
}