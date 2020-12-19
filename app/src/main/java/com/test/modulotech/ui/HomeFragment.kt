package com.test.modulotech.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.modulotech.R
import com.test.modulotech.base.BaseFragment
import com.test.modulotech.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*

class HomeFragment: BaseFragment() {
    override val resLayout: Int = R.layout.home_fragment

    lateinit var viewModel: HomeViewModel

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
                    view?.findNavController()?.navigate(R.id.profileFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupAdapter() {
        val layout = LinearLayoutManager(context)
        layout.orientation = LinearLayoutManager.VERTICAL
        equipmentList.layoutManager = layout
        equipmentList.adapter = viewModel.adapter
    }
}