package com.test.modulotech.model

import java.io.Serializable

data class ResponseModel(
     val devices: List<DeviceData>,
     val user: UserModel
)

data class UserModel(
     val firstName: String? = null,
     val lastName: String? = null,
     val address: AdressModel? = null,
     val birthDate: Long? = null
): Serializable

data class AdressModel(
     val city: String,
     val postalCode: Long,
     val street: String,
     val streetCode: String,
     val country: String
): Serializable

/*
open class deviceList (
     var id: Int,
     val deviceName: String?,
    val intensity: Int?,
    val mode: ModeStatus?,
    val productType: ProductType?,
    val position: Int?,
    val temperature: Double?
)

interface DeviceInterface {
}

open class BasicDevice() {
    var id: Int? = null
    var deviceName: String? = null
    var productType: ProductType? = null
}

class LightDevice(): BasicDevice() {
    var intensity: Int? = null
    var mode: ModeStatus? = null
}

class HeaterDevice(): BasicDevice() {
    var mode: Int? = null
    var temperature: Double? = null
}

class RollerShutterDevice(): BasicDevice() {
    var position: Int? = null
}
*/
