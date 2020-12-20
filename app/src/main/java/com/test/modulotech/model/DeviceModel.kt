package com.test.modulotech.model

import java.io.Serializable

sealed class DeviceData: Serializable {
    data class LightModel(
        val id: Int?= null,
        val deviceName: String? = null,
        val intensity: Int? = null,
        val mode: ModeStatus? = null,
        val productType: ProductType? = null,
        var listener: ((LightModel) -> Unit)? = null
    ): DeviceData()

    data class RollerShutterModel(
        val id: Int?= null,
        val deviceName: String? = null,
        val position: Int? = null,
        val productType: ProductType? = null
    ): DeviceData()

    data class HeaterModel(
        val id: Int?= null,
        val deviceName: String? = null,
        val mode: ModeStatus? = null,
        val temperature: Double? = null,
        val productType: ProductType? = null
    ): DeviceData()
}

enum class ModeStatus {
    ON, OFF
}

enum class ProductType {
    RollerShutter,
    Heater,
    Light
}