package com.test.modulotech.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.test.modulotech.R
import com.test.modulotech.base.BaseFragment
import com.test.modulotech.model.DeviceData
import com.test.modulotech.model.ProductType
import com.test.modulotech.model.UserModel
import com.test.modulotech.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.custom_profile_pic.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*

class HomeFragment: BaseFragment() {
    override val resLayout: Int = R.layout.home_fragment

    lateinit var viewModel: HomeViewModel
    private var user: UserModel? = null

    private var snackbar: Snackbar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.get()

        setupAdapter()
        buildListeners()
    }

    private fun buildListeners() {

        materialToolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.profile -> {
                    if(user != null) {
                        val directions = HomeFragmentDirections.homeFragmentToProfileFragment(user!!)
                        findNavController().navigate(directions)
                        true
                    }
                    else
                        false
                }
                else -> false
            }
        }

        viewModel.user.observe(viewLifecycleOwner, Observer {
            user = it
        })

        viewModel.navigateToDevice.observe(viewLifecycleOwner, Observer {
            when(it) {
                is DeviceData.LightModel -> {
                    it.listener = {device -> viewModel.updateDevice(device, ProductType.Light)}
                    findNavController().navigate(HomeFragmentDirections.homeFragmentToLightModifier(it))
                }
                is DeviceData.HeaterModel -> {
                    it.listener = {device -> viewModel.updateDevice(device, ProductType.Heater)}
                    findNavController().navigate(HomeFragmentDirections.homeFragmentToHeaterModifier(it))
                }
                is DeviceData.RollerShutterModel -> {
                    it.listener = {device -> viewModel.updateDevice(device, ProductType.RollerShutter)}
                    findNavController().navigate(HomeFragmentDirections.homefragmentToRollerModifier(it))
                }
            }

        })

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(equipmentList)

        viewModel.progressbarIsVisible.observe(viewLifecycleOwner, {
            loading.visibility = it
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            if(it == null) {
                snackbar?.dismiss()
            } else {
                snackbar = Snackbar.make(homeLayout, it, Snackbar.LENGTH_INDEFINITE)
                snackbar?.setAction("Retry", viewModel.errorClickListener)
                snackbar?.show()
            }
        })
    }

    val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.deleteDevice(viewHolder.adapterPosition)
        }

    }

    private fun setupAdapter() {
        val layout = LinearLayoutManager(context)
        layout.orientation = LinearLayoutManager.VERTICAL
        equipmentList.layoutManager = layout
        equipmentList.adapter = viewModel.adapter
    }


}