package com.test.modulotech.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
sealed class DeviceData: Serializable {
    @Entity
    data class LightModel(
        @field:PrimaryKey
        val id: Int?= null,
        val deviceName: String? = null,
        val intensity: Int? = null,
        val mode: ModeStatus? = null,
        val productType: ProductType? = null,
        var listener: ((LightModel) -> Unit)? = null
    ): DeviceData()

    @Entity
    data class RollerShutterModel(
        @field:PrimaryKey
        val id: Int?= null,
        val deviceName: String? = null,
        val position: Int? = null,
        val productType: ProductType? = null,
        var listener: ((RollerShutterModel) -> Unit)? = null
    ): DeviceData()

    @Entity
    data class HeaterModel(
        @field:PrimaryKey
        val id: Int?= null,
        val deviceName: String? = null,
        val mode: ModeStatus? = null,
        val temperature: Double? = null,
        val productType: ProductType? = null,
        var listener: ((HeaterModel) -> Unit)? = null
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