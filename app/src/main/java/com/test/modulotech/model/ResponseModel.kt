package com.test.modulotech.model

import androidx.room.Entity
import java.io.Serializable

@Entity
data class ResponseModel(
     val devices: List<DeviceData>,
     val user: UserModel
)

data class UserModel(
     var firstName: String? = null,
     var lastName: String? = null,
     var address: AdressModel? = null,
     var birthDate: Long? = null
): Serializable

data class AdressModel(
     var city: String,
     var postalCode: Long,
     var street: String,
     var streetCode: String,
     var country: String
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
