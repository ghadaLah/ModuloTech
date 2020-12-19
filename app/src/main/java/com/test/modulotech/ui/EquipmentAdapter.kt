package com.test.modulotech.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.modulotech.R
import com.test.modulotech.model.DeviceData
import kotlinx.android.synthetic.main.heater_item.view.*
import kotlinx.android.synthetic.main.light_item.view.*
import kotlinx.android.synthetic.main.roller_shatter_item.view.*

class EquipmentAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var equipmentList: MutableList<DeviceData> = mutableListOf()

    companion object {
        const val lightType = 1
        const val rollerShutterType = 2
        const val heaterType = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, typeView: Int): RecyclerView.ViewHolder {
        if(typeView == lightType) {
            return LightViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.light_item, parent, false))
        } else if(typeView == heaterType) {
            return HeaterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.heater_item, parent, false))
        } else {
            return RollerShutterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.roller_shatter_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(equipmentList[position]) {
            is DeviceData.LightModel -> (holder as LightViewHolder).bindView(equipmentList[position] as DeviceData.LightModel)
            is DeviceData.HeaterModel -> (holder as HeaterViewHolder).bindView(equipmentList[position] as DeviceData.HeaterModel)
            is DeviceData.RollerShutterModel -> (holder as RollerShutterViewHolder).bindView(equipmentList[position] as DeviceData.RollerShutterModel)
        }
    }

    override fun getItemCount(): Int = equipmentList.size

    override fun getItemViewType(position: Int): Int {
        when(equipmentList[position]) {
            is DeviceData.LightModel -> return lightType
            is DeviceData.HeaterModel -> return heaterType
            is DeviceData.RollerShutterModel -> return rollerShutterType
            else -> return lightType
        }
    }

    fun addDevices(devices: List<DeviceData>) {
        equipmentList.clear()
        if(!devices.isNullOrEmpty()) {
            equipmentList.addAll(devices)
            notifyDataSetChanged()
        }
    }

    inner class LightViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(device: DeviceData.LightModel) {
            itemView.lightName.text = device.deviceName
        }
    }

    inner class HeaterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(device: DeviceData.HeaterModel) {
            itemView.heaterName.text = device.deviceName
        }
    }

    inner class RollerShutterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(device: DeviceData.RollerShutterModel) {
            itemView.rollerShutterName.text = device.deviceName
        }
    }
}