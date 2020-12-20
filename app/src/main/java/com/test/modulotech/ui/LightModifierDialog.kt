package com.test.modulotech.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.test.modulotech.R
import com.test.modulotech.model.DeviceData
import com.test.modulotech.model.ModeStatus
import kotlinx.android.synthetic.main.light_modifier_dialog.*

class LightModifierDialog: DialogFragment() {
    val args: LightModifierDialogArgs by navArgs()

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

        setView(args.lightArgs as DeviceData.LightModel)
    }

    fun setView(lightData: DeviceData.LightModel) {
        when(lightData.mode) {
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

        val intensity = lightData.intensity ?: 0
        lightIntensity.setText(intensity.toString())
    }
}