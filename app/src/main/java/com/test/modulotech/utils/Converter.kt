package com.test.modulotech.utils

import androidx.room.TypeConverter
import com.test.modulotech.model.DeviceData
import com.test.modulotech.model.ModeStatus
import com.test.modulotech.model.ProductType
import java.util.function.Function

class Converter {
    @TypeConverter
    fun convertModeToString(mode: ModeStatus): String = mode.name

    @TypeConverter
    fun convertStringToMode(mode: String): ModeStatus = enumValueOf(mode)

    @TypeConverter
    fun convertProductTypeToString(productType: ProductType): String = productType.name

    @TypeConverter
    fun convertStringToProductType(value: String): ProductType = enumValueOf(value)

    @TypeConverter
    fun convertLightListenerFunctionToUnit(listener: ((DeviceData.LightModel) -> Unit)) = {}

    @TypeConverter
    fun convertUnitToLightListenerFunction(listener: DeviceData.LightModel) = Function<DeviceData.LightModel, Unit>{}

    @TypeConverter
    fun convertHeaterListenerFunctionToUnit(listener: ((DeviceData.HeaterModel) -> Unit)) = {}

    @TypeConverter
    fun convertRollerListenerFunctionToUnit(listener: ((DeviceData.RollerShutterModel) -> Unit)) = {}
}