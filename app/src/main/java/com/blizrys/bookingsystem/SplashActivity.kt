package com.blizrys.bookingsystem

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.blizrys.bookingsystem.databinding.ActivitySplashBinding
import com.blizrys.bookingsystem.services.SessionManager
import com.blizrys.bookingsystem.services.SessionSingleton.currentUser
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import utilities.hideSystemUI
import java.lang.Runnable

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val SPLASH_TIME_OUT: Long = 3000 // This is the loading time of the splash screen

    // See: https://developer.android.com/training/basics/intents/result
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }
    // Choose authentication providers
    val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    // Create and launch sign-in intent
    val signInIntent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .setLogo(R.drawable.ic_launcher_foreground) // Set logo drawabl
//        .setTheme(R.style.Theme_BookingSystem) // Set theme
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        CoroutineScope(Dispatchers.Main).launch {
            Log.i("SplashActivity", "Launch all necessary services")

            // Minimum Splash time (Optional)
            val minSplash = async(Dispatchers.IO) { minSplashDelay(SPLASH_TIME_OUT) }
            minSplash.await()

//            FirebaseUser currentUser = mAuth.getCurrentUser()
            val sessionManager = SessionManager()

            // check if have save credentials
            if(currentUser != null){ // TODO: remove FORCE
                Log.i("SplashActivity","Can Redirect to MainActivity")
                // Start your app main activity & close this activity
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
            else{
                // Start your app config activity & close this activity
//                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                signInLauncher.launch(signInIntent)
            }

        }
    }
    private suspend fun minSplashDelay(DELAY_MS: Long = 3000): Long {
        delay(DELAY_MS)
        Log.i("SplashActivity", "minSplashDelay Returned")
        return DELAY_MS
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            hideSystemUI()
        }
    }
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                Log.i("SplashActivity",user.uid)
                // Start your app main activity & close this activity
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
            // ...
        } else {
            Log.i("SplashActivity","Sign up failed, please try again")
            Toast.makeText(applicationContext,"Sign up failed, please try again", Toast.LENGTH_SHORT).show()
//            signInLauncher.launch(signInIntent)
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
}