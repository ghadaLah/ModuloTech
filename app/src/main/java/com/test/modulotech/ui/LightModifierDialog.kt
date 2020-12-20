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
import kotlinx.android.synthetic.main.light_modifier_dialog.*

class LightModifierDialog: DialogFragment() {
    val args: LightModifierDialogArgs by navArgs()

    private var id: Int?= null
    private var deviceName: String? = null
    private var intensity: Int? = null
    private var mode: ModeStatus? = null
    private var productType: ProductType? = null
    private var listener: ((DeviceData.LightModel) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.let {
            val backColor = ColorDrawable(Color.TRANSPARENT)
            val inset = InsetDrawable(backColor, 64, 0, 64, 0)
            it.setBackgroundDrawable(inset)
        }
        return inflater.inflate(R.layout.light_modifier_dialog, container, false)
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

        getArgs(args.lightArgs as DeviceData.LightModel)
        setView()
        setListeners()
    }

    private fun getArgs(args: DeviceData.LightModel) {
        this.id = args.id
        this.deviceName = args.deviceName
        this.intensity = args.intensity
        this.mode = args.mode
        this.productType = args.productType
        this.listener = args.listener
    }

    fun setView() {
        when(mode) {
            ModeStatus.ON -> {
                lightIc.setImageResource(R.drawable.ic_light_on)
                lightModeSwitcher.isChecked = true
                lightModeSwitcher.textOn = "ON"
            }
            ModeStatus.OFF -> {
                lightIc.setImageResource(R.drawable.ic_light_off)
                lightModeSwitcher.isChecked = false
                lightModeSwitcher.textOff = "OFF"
            }
        }

        val intensity = intensity ?: 0
        lightIntensity.setText(intensity.toString())
    }

    fun setListeners() {
        lightModeSwitcher.setOnCheckedChangeListener { view, isChecked ->
            if(isChecked) {
                lightIc.setImageResource(R.drawable.ic_light_on)
                lightModeSwitcher.isChecked = true
                lightModeSwitcher.textOn = "ON"
                this.mode = ModeStatus.ON
            } else {
                lightIc.setImageResource(R.drawable.ic_light_off)
                lightModeSwitcher.isChecked = false
                lightModeSwitcher.textOff = "OFF"
                this.mode = ModeStatus.OFF
            }
        }
        lightIntensity.doAfterTextChanged {
            if(!it.isNullOrBlank()) {
                val intensity  = it.toString().toInt()
                if(intensity >= 0 && intensity <= 100)
                    this.intensity = intensity
                else
                    lightIntensity.error = getString(R.string.intensity_bounds_error)
            }
        }
        lightValidateBtn.setOnClickListener {
            listener?.invoke(DeviceData.LightModel(id, deviceName, intensity, mode, productType))
            dismiss()
        }

        lightCancelBtn.setOnClickListener {
            dismiss()
        }
    }
}