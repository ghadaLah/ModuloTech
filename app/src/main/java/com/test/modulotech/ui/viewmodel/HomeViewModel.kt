package com.test.modulotech.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.test.modulotech.base.BaseViewModel
import com.test.modulotech.model.*
import com.test.modulotech.network.DeviceApi
import com.test.modulotech.ui.DeviceClickListener
import com.test.modulotech.ui.EquipmentAdapter
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel(val dao: DevicesDao): BaseViewModel(), DeviceClickListener {

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
        disposable.add(
            Single.fromCallable { dao.data }
            .map { data ->
                if(data == null)
                    deviceApi.getDevices().map {response ->
                        dao.insertAll(response)
                        Single.just(response)
                    }
                else
                    Single.just(data)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                user.postValue(it.user)
                devices.addAll(it.devices)
                adapter.updateDeviceList(it.devices)
            }, {
                errorMessage.postValue("Error get devices $it")
            })
        )
    }

    override fun showDeviceModifier(device: DeviceData) {
        navigateToDevice.postValue(device)
    }

    override fun deleteDevice(position: Int) {
        devices.removeAt(position)
        adapter.updateDeviceList(devices)
    }

    fun updateDevice(device: DeviceData, deviceType: ProductType) {
        val deviceIndex = when(deviceType) {
            ProductType.RollerShutter -> devices.indexOfFirst { it is DeviceData.RollerShutterModel && it.id == (device as DeviceData.RollerShutterModel).id }
            ProductType.Heater -> devices.indexOfFirst { it is DeviceData.HeaterModel && it.id == (device as DeviceData.HeaterModel).id }
            ProductType.Light -> devices.indexOfFirst { it is DeviceData.LightModel && it.id == (device as DeviceData.LightModel).id }
        }
        if(deviceIndex >= 0) {
            devices.set(deviceIndex, device)
            adapter.updateDeviceList(devices)
        }
    }


}