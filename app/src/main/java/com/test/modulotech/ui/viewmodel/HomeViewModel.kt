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
                adapter.addDevices(it.devices)
            }, {
                errorMessage.postValue("Error get devices $it")
            })
    }
}