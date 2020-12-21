package com.test.modulotech.ui.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
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
    var progressbarIsVisible = MutableLiveData<Int>()

    val devices = mutableListOf<DeviceData>()

    var disposable = CompositeDisposable()
    val errorClickListener = View.OnClickListener { get() }

    fun get() {
        deviceApi.getDevices()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { showLoading() }
            .subscribe({
                user.postValue(it.user)
                devices.addAll(it.devices)
                adapter.updateDeviceList(it.devices)
                progressbarIsVisible.postValue(View.GONE)
            }, {
                showError(it)
            })
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

    fun showLoading() {
        progressbarIsVisible.postValue(View.VISIBLE)
        errorMessage.postValue(null)
    }

    fun showError(exception: Throwable) {
        progressbarIsVisible.postValue(View.GONE)
        errorMessage.postValue("An error occured while retrieving devices info - [$exception]")
    }

}