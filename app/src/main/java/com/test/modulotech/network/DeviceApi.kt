package com.test.modulotech.network

import com.test.modulotech.model.DeviceData
import com.test.modulotech.model.ResponseModel
import io.reactivex.Single
import retrofit2.http.GET

interface DeviceApi {
    @GET("data.json")
    fun getDevices(): Single<ResponseModel>
}