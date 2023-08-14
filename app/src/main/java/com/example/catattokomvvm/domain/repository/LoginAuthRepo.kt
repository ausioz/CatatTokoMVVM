package com.example.catattokomvvm.domain.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class LoginAuthRepo(private val application: Application) {


//    firebase and firestore data
//    user1 = adi@catattoko.com ; pass = 123456 ; role = admin
//    user2 = budi@catattoko.com ; pass = 123456 ; role = admin
//    user3 = adi@catattoko.com ; pass = 123456 ; role = basic

    val firebaseUserMutableLiveData: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val userLoggedMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val userRoleMutableLiveData: MutableLiveData<String> = MutableLiveData()
    val userNameMutableLiveData: MutableLiveData<String> = MutableLiveData()
    val nikMutableLiveData: MutableLiveData<String> = MutableLiveData()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fStore: FirebaseFirestore = FirebaseFirestore.getInstance()


    init {
        if (auth.currentUser != null) {
            firebaseUserMutableLiveData.postValue(auth.currentUser)
            userLoggedMutableLiveData.postValue(true)
            checkUserRole(auth.currentUser?.uid)
        }
    }

    fun register(email: String?, pass: String?) {
        auth.createUserWithEmailAndPassword(email!!, pass!!).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseUserMutableLiveData.postValue(auth.currentUser)
            } else {
                Toast.makeText(application, "Register Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun login(email: String?, pass: String?) {
        auth.signInWithEmailAndPassword(email!!, pass!!).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseUserMutableLiveData.postValue(auth.currentUser)
                checkUserRole(task.result.user?.uid)

            } else {
                Toast.makeText(
                    application,
                    "Login Error: Invalid mail and/or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun checkUserRole(uid: String?) {
        val documentReference = fStore.collection("users").document(uid!!)

        documentReference.get().addOnSuccessListener {
//            Toast.makeText(application, it.getString("role")!!, Toast.LENGTH_SHORT).show()
            if (it.getString("role") == "admin") {
                userRoleMutableLiveData.postValue("admin")
            } else {
                userRoleMutableLiveData.postValue("basic")
            }
            userNameMutableLiveData.postValue(it.getString("nama"))
            nikMutableLiveData.postValue(it.getString("nik"))

        }
        documentReference.get().addOnFailureListener {
            Toast.makeText(application, "Role Error", Toast.LENGTH_SHORT).show()
        }
    }

    fun signOut() {
        auth.signOut()
        userLoggedMutableLiveData.postValue(false)
        userRoleMutableLiveData.postValue("")
    }
}