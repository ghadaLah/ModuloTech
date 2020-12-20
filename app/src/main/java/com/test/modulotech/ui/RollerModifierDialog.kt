package com.test.modulotech.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.test.modulotech.R
import com.test.modulotech.model.DeviceData
import com.test.modulotech.model.ModeStatus
import com.test.modulotech.model.ProductType
import kotlinx.android.synthetic.main.roller_shatter_modifier_dialog.*

class RollerModifierDialog: DialogFragment() {
    val args: RollerModifierDialogArgs by navArgs()

    private var id: Int?= null
    private var deviceName: String? = null
    private var position: Int? = null
    private var mode: ModeStatus? = null
    private var productType: ProductType? = null
    private var listener: ((DeviceData.RollerShutterModel) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.let {
            val backColor = ColorDrawable(Color.TRANSPARENT)
            val inset = InsetDrawable(backColor, 64, 0, 64, 0)
            it.setBackgroundDrawable(inset)
        }
        return inflater.inflate(R.layout.roller_shatter_modifier_dialog, container, false)
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

        getArgs(args.rollerArgs as DeviceData.RollerShutterModel)
        setListeners()
    }

    private fun getArgs(args: DeviceData.RollerShutterModel) {
        this.id = args.id
        this.deviceName = args.deviceName
        this.position = args.position
        this.productType = args.productType
        this.listener = args.listener
    }

    fun setSeekbarView(progress: Int) {
        if(progress < 20)
            rollerIc.setImageResource(R.drawable.ic_night)
        else if (progress in 20..60)
            rollerIc.setImageResource(R.drawable.ic_roller_medium)
        else
            rollerIc.setImageResource(R.drawable.ic_open_roller)

        rollerPosition.text = progress.toString()
    }

    fun setListeners() {
        if(position != null) {
            rollerSeekbar.progress = position!!
            setSeekbarView(position!!)
        }
        rollerSeekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                setSeekbarView(p1)
                position = p1
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
        rollerValidateBtn.setOnClickListener {
            listener?.invoke(DeviceData.RollerShutterModel(id, deviceName, position, productType))
            dismiss()
        }

        rollerCancelBtn.setOnClickListener {
            dismiss()
        }
    }

}