package com.test.modulotech.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.test.modulotech.R
import com.test.modulotech.model.DeviceData
import com.test.modulotech.model.ModeStatus
import com.test.modulotech.model.ProductType
import kotlinx.android.synthetic.main.heater_modifier_dialog.*

class HeaterModifierDialog: DialogFragment() {
    val args: HeaterModifierDialogArgs by navArgs()

    private var id: Int?= null
    private var deviceName: String? = null
    private var temperature: Double? = null
    private var mode: ModeStatus? = null
    private var productType: ProductType? = null
    private var listener: ((DeviceData.HeaterModel) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.let {
            val backColor = ColorDrawable(Color.TRANSPARENT)
            val inset = InsetDrawable(backColor, 64, 0, 64, 0)
            it.setBackgroundDrawable(inset)
        }
        return inflater.inflate(R.layout.heater_modifier_dialog, container, false)
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.attributes?.let { attributes ->
            attributes.width = ViewGroup.LayoutParams.MATCH_PARENT
            attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog?.window?.attributes = attributes
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getArgs(args.heaterArgs as DeviceData.HeaterModel)
        setView()
        setListeners()
    }

    private fun getArgs(args: DeviceData.HeaterModel) {
        this.id = args.id
        this.deviceName = args.deviceName
        this.temperature = args.temperature
        this.mode = args.mode
        this.productType = args.productType
        this.listener = args.listener
    }

    fun setView() {
        when(mode) {
            ModeStatus.ON -> {
                heaterIc.setImageResource(R.drawable.ic_light_on)
                heaterModeSwitcher.isChecked = true
                heaterModeSwitcher.textOn = "ON"
            }
            ModeStatus.OFF -> {
                heaterIc.setImageResource(R.drawable.ic_light_off)
                heaterModeSwitcher.isChecked = false
                heaterModeSwitcher.textOff = "OFF"
            }
        }

        val temperature = temperature ?: 0.0
        heaterTemperature.setText(temperature.toString())
    }

    fun setListeners() {
        heaterModeSwitcher.setOnCheckedChangeListener { view, isChecked ->
            if(isChecked) {
                heaterIc.setImageResource(R.drawable.ic_light_on)
                heaterModeSwitcher.isChecked = true
                heaterModeSwitcher.textOn = "ON"
                this.mode = ModeStatus.ON
            } else {
                heaterIc.setImageResource(R.drawable.ic_light_off)
                heaterModeSwitcher.isChecked = false
                heaterModeSwitcher.textOff = "OFF"
                this.mode = ModeStatus.OFF
            }
        }
        heaterTemperature.doAfterTextChanged {
            if(!it.isNullOrBlank()) {
                val temperature  = it.toString().toDouble()
                if(temperature in 7.0..28.0)
                    this.temperature = temperature
                else
                    heaterTemperature.error = getString(R.string.temperature_bounds_error)
            }
        }
        heaterValidateBtn.setOnClickListener {
            listener?.invoke(DeviceData.HeaterModel(id, deviceName,mode,  temperature, productType))
            dismiss()
        }

        heaterCancelBtn.setOnClickListener {
            dismiss()
        }
    }
}