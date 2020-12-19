package com.test.modulotech.di

import com.google.gson.GsonBuilder
import com.test.modulotech.model.DeviceData
import com.test.modulotech.network.DeviceApi
import com.test.modulotech.network.DeviceInterfaceDeserializer
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {
    const val BASE_URL = "http://storage42.com/modulotest/"

    @Singleton
    @Provides
    fun provideDeviceService(retrofit: Retrofit) =
        retrofit.create(DeviceApi::class.java)

    @Singleton
    @Provides
    fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    val builder = GsonBuilder()
        .registerTypeAdapter(DeviceData::class.java, DeviceInterfaceDeserializer())
    val gson = builder.create()

}