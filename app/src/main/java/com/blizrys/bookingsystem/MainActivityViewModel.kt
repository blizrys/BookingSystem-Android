package com.blizrys.bookingsystem

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavGraph
import androidx.navigation.findNavController

class MainActivityViewModel: ViewModel() {
    private var toastMessage = MutableLiveData<String?>()
    val ToastMessage : LiveData<String?>
        get() = toastMessage

    @SuppressLint("RestrictedApi")
    fun displayNavigationPath(view: View): String{

        val breadcrumb = view.findNavController()
            .backStack
            .map { x ->
                x.destination
            }
            .filterNot { x ->
                x is NavGraph
            }
            .joinToString(" > ") { x ->
                x.displayName.split('/')[1]
            }

        toastMessage.value = breadcrumb
        return breadcrumb
    }
}