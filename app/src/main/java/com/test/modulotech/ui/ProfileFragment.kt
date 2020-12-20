package com.test.modulotech.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.test.modulotech.R
import com.test.modulotech.base.BaseFragment
import com.test.modulotech.model.UserModel
import kotlinx.android.synthetic.main.profile_fragment.*
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class ProfileFragment: BaseFragment() {
    override val resLayout: Int = R.layout.profile_fragment
    private val args: ProfileFragmentArgs by navArgs()

    private var user: UserModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getArgs()
        buildView()
        buildListeners()
    }

    private fun getArgs() {
        args.let { userArgs ->
            user = userArgs.data
        }
    }

    private fun buildListeners() {
        materialToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        userFirstName.doAfterTextChanged { name ->
            if(!name.isNullOrBlank())
                user?.firstName = name.toString()
        }
        userLastName.doAfterTextChanged { name ->
            if(!name.isNullOrBlank())
                user?.lastName = name.toString()
        }
        context?.let {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            userBirthday.setOnClickListener { name ->
                val dpd = DatePickerDialog(
                    it,
                    { view, year, monthOfYear, dayOfMonth ->
                        val cal = Calendar.getInstance()
                        cal.set(year, monthOfYear, dayOfMonth)
                        user?.birthDate = cal.timeInMillis
                        userBirthday.setText("$dayOfMonth/${monthOfYear+1}/$year")
                    }, year, month, day
                )
                dpd.show()
            }
        }
        streetCodeText.doAfterTextChanged {
            if(!it.isNullOrBlank())
                user?.address?.streetCode = it.toString()
        }
        streetText.doAfterTextChanged {
            if(!it.isNullOrBlank())
                user?.address?.street = it.toString()
        }
        cityText.doAfterTextChanged {
            if(!it.isNullOrBlank())
                user?.address?.city = it.toString()
        }
        postalCodeText.doAfterTextChanged {
            if(!it.isNullOrBlank())
                user?.address?.postalCode = it.toString().toLong()
        }
        countryText.doAfterTextChanged {
            if(!it.isNullOrBlank())
                user?.address?.country = it.toString()
        }
    }

    private fun buildView() {
        user?.let { user ->
            userFirstName.setText(user.firstName)
            userLastName.setText(user.lastName)
            //birthdate
            val birthday = Date(user.birthDate!!)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            userBirthday.setText(dateFormat.format(birthday))
            //adress
            streetCodeText.setText(user.address?.streetCode)
            streetText.setText(user.address?.street)
            cityText.setText(user.address?.city)
            postalCodeText.setText(user.address?.postalCode.toString())
            countryText.setText(user.address?.country)
        }
    }
}