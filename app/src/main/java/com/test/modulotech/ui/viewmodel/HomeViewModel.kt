package com.test.modulotech.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.modulotech.base.BaseViewModel
import com.test.modulotech.model.*
import com.test.modulotech.network.DeviceApi
import com.test.modulotech.ui.DeviceClickListener
import com.test.modulotech.ui.EquipmentAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel(): BaseViewModel(), DeviceClickListener {

    @Inject
    lateinit var deviceApi: DeviceApi
    var adapter  = EquipmentAdapter(this)
    var devicesList = MutableLiveData<List<DeviceData>>()
    var errorMessage = MutableLiveData<String>()
    var user = MutableLiveData<UserModel>()
    var navigateToDevice = MutableLiveData<DeviceData>()

    val devices = mutableListOf<DeviceData>()

    var disposable = CompositeDisposable()

    fun get() {
        deviceApi.getDevices()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
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
                devices.addAll(it.devices)
                adapter.addDevices(it.devices)
            }, {
                errorMessage.postValue("Error get devices $it")
            })
    }

    override fun showDeviceModifier(device: DeviceData) {
        navigateToDevice.postValue(device)
    }

    fun  updateDevice(device: DeviceData, deviceType: ProductType) {
        val deviceIndex = when(deviceType) {
            ProductType.RollerShutter -> devices.indexOfFirst { it is DeviceData.RollerShutterModel && it.id == (device as DeviceData.RollerShutterModel).id }
            ProductType.Heater -> devices.indexOfFirst { it is DeviceData.HeaterModel && it.id == (device as DeviceData.HeaterModel).id }
            ProductType.Light -> devices.indexOfFirst { it is DeviceData.LightModel && it.id == (device as DeviceData.LightModel).id }
        }
        if(deviceIndex >= 0) {
            devices.set(deviceIndex, device)
            adapter.addDevices(devices)
        }
    }
}