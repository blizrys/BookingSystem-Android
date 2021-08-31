package com.blizrys.bookingsystem.ui.dashboard

import android.os.Bundle
import android.util.Log
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
import com.blizrys.bookingsystem.databinding.FragmentHomeBinding
import com.blizrys.bookingsystem.services.FirestoreManager
import com.blizrys.bookingsystem.services.SessionSingleton
import com.blizrys.bookingsystem.ui.home.HomeViewModel
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding
    private lateinit var viewModel: DashboardViewModel

    private lateinit var database:FirebaseDatabase
    private lateinit var myRef:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        binding.lifecycleOwner = this
        // Write a message to the database
        database = Firebase.database
        myRef = database.getReference("message")

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                Log.d("DashboardFragment", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("DashboardFragment", "Failed to read value.", error.toException())
            }
        })

        FirestoreManager().listenData()

        binding.button2.setOnClickListener {
//            write_data()
            // Create a new user with a first and last name
            val user = hashMapOf(
                "first" to "Ada",
                "last" to "Lovelace",
                "born" to 1815
            )
            FirestoreManager().addData(user)

            // Create a new user with a first, middle, and last name
            val user2 = hashMapOf(
                "first" to "Alan",
                "middle" to "Mathison",
                "last" to "Turing",
                "born" to 1912
            )
            FirestoreManager().addData(user2)

//            FirestoreManager().readData()
        }

        Firebase.database.reference.child("schedule").get().addOnSuccessListener {
            Log.i("DashboardFragment", "Got value ${it.value.toString()}")
        }.addOnFailureListener{
            Log.e("DashboardFragment", "Error getting data", it)
        }
        return binding.root
    }

    fun write_data(){
        Log.d("DashboardFragment", "WRITE!")
        myRef.setValue("Hello, World!")
    }
}