package com.blizrys.bookingsystem.services

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FirestoreManager {
    private val database = Firebase.firestore

    companion object{
        const val TAG = "FirestoreManager"
    }

    fun addData(user:Any){

        // Add a new document with a generated ID
        database.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun readData(){
        database.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun listenData(){

        val docRef = database.collection("users")
        docRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null ) {
                for (document in snapshot) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }
}