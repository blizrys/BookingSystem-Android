package com.blizrys.bookingsystem.services

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.blizrys.bookingsystem.services.SessionSingleton.currentUser
import com.blizrys.bookingsystem.services.SessionSingleton.currentUserLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object SessionSingleton {

    var currentUser: FirebaseUser?
        get() = currentUserLiveData.value
        set(value) {
            currentUserLiveData.postValue(value)
        }

    val currentUserLiveData = MutableLiveData<FirebaseUser?>()

}

class SessionManager {
    init{
        getCurrentUser()
    }

    fun getCurrentUser(){
        Log.d("SessionManager","getCurrentUser")
        val user = FirebaseAuth.getInstance().currentUser

        currentUserLiveData.value = user

        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
            Log.i("SessionManager","name: $name")
            Log.i("SessionManager","email: $email")
            Log.i("SessionManager","photoUrl: $photoUrl")
            Log.i("SessionManager","emailVerified: $emailVerified")
            Log.i("SessionManager","uid: $uid")

        }
    }
}
