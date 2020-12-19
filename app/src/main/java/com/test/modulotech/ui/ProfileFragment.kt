package com.test.modulotech.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.test.modulotech.base.BaseFragment
import com.test.modulotech.*
import com.test.modulotech.model.UserModel
import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment: BaseFragment() {
    override val resLayout: Int = R.layout.profile_fragment
    private val args: ProfileFragmentArgs by navArgs()

    private var user: UserModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getArgs()
        buildView()
    }

    private fun getArgs() {
        args.let { userArgs ->
            user = userArgs.data
        }
    }

    private fun buildView() {
        userFirstName.setText(user?.firstName)
        userLastName.setText(user?.lastName)
    }
}