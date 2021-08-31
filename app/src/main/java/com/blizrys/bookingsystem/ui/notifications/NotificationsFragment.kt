package com.blizrys.bookingsystem.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blizrys.bookingsystem.R
import com.blizrys.bookingsystem.databinding.FragmentDashboardBinding
import com.blizrys.bookingsystem.databinding.FragmentNotificationsBinding
import com.blizrys.bookingsystem.services.SessionSingleton
import com.blizrys.bookingsystem.ui.dashboard.DashboardViewModel
import com.blizrys.bookingsystem.ui.home.HomeViewModel

class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var viewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications, container, false)
        viewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        binding.lifecycleOwner = this

        return binding.root
    }

}