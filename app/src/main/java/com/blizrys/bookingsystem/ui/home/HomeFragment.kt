package com.blizrys.bookingsystem.ui.home

import android.content.pm.PackageManager
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
import com.blizrys.bookingsystem.databinding.FragmentHomeBinding
import com.blizrys.bookingsystem.services.SessionSingleton

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.lifecycleOwner = this

        SessionSingleton.currentUserLiveData.observe(this, Observer {
            if(it != null){
                binding.textViewName.text = it.displayName
            }

        })
        return binding.root
    }

}