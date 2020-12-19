package com.test.modulotech.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.modulotech.base.BaseViewModel
import com.test.modulotech.model.*
import com.test.modulotech.network.DeviceApi
import com.test.modulotech.ui.EquipmentAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel(): BaseViewModel() {

    @Inject
    lateinit var deviceApi: DeviceApi
    var adapter  = EquipmentAdapter()
    var devicesList = MutableLiveData<List<DeviceData>>()
    var errorMessage = MutableLiveData<String>()
    var user = MutableLiveData<UserModel>()

    val fictifList = listOf<DeviceData>(
        DeviceData.LightModel( 1,"Lampe - Cuisine", 50, ModeStatus.ON, ProductType.Light),
        DeviceData.RollerShutterModel( 2,"Volet roulant - Salon", 70, ProductType.RollerShutter),
        DeviceData.HeaterModel(3,"Radiateur - Chambre", ModeStatus.OFF, 20.0, ProductType.Heater),
        DeviceData.LightModel( 4,"Lampe - salon", 100, ModeStatus.ON, ProductType.Light),
        DeviceData.RollerShutterModel( 5,"Volet roulant", 0, ProductType.RollerShutter),
        DeviceData.HeaterModel(6,"Radiateur - Salon", ModeStatus.OFF, 18.0, ProductType.Heater))

    var disposable = CompositeDisposable()

    fun get() {
        var aa = mutableListOf<DeviceData>()
        deviceApi.getDevices()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("getdevices", "getdevices success $it")
                user.postValue(it.user)
                it.devices.forEach { device ->
                    /*when(device.productType) {
                        ProductType.RollerShutter -> aa.add(device as DeviceData)
                        ProductType.Heater -> TODO()
                        ProductType.Light -> TODO()
                        null -> TODO()
                    }*/
                }
                //devicesList.postValue(it.devices)
                adapter.addDevices(it.devices)
                //devicesList.postValue(it.devices)
            }, {
                Log.d("getdevices", "getdevices error $it")
                errorMessage.postValue("Error get devices $it")
            })
    }
}