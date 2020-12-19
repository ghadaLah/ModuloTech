package com.test.modulotech.network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.test.modulotech.model.*
import java.lang.reflect.Type

class DeviceInterfaceDeserializer: JsonDeserializer<DeviceData> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): DeviceData? {
        /*val jsonObject = json?.asJsonObject?.getAsJsonArray("devices")
        context?.let { context ->
            jsonObject?.forEach {device ->
                val set = device.asJsonObject.entrySet()
                val jsonElement = set.iterator().next().value
                val productType = jsonElement.asJsonObject.getAsJsonArray("productType")
                when(productType.asString) {
                    "Light" -> return context.deserialize<DeviceData>(json, DeviceData.LightModel::class.java)
                    "Heater" -> return context.deserialize<DeviceData>(json, DeviceData.HeaterModel::class.java)
                    "RollerShutter" -> return context.deserialize<DeviceData>(json, DeviceData.RollerShutterModel::class.java)
                }
            }
        }*/

        val jsonObsect =  json?.asJsonObject?.get("productType")
        jsonObsect?.let {element ->
            when(element.asString) {
                "Light" -> return context?.deserialize<DeviceData>(json, DeviceData.LightModel::class.java)
                "Heater" -> return context?.deserialize<DeviceData>(json, DeviceData.HeaterModel::class.java)
                "RollerShutter" -> return context?.deserialize<DeviceData>(json, DeviceData.RollerShutterModel::class.java)
                else -> null
            }
        }

        return null
    }
}