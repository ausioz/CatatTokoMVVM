package com.example.catattokomvvm.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.catattokomvvm.repository.LoginAuthRepo
import com.google.firebase.auth.FirebaseUser


class LoginViewModel(application: Application):ViewModel() {

    private val repository = LoginAuthRepo(application)
    private val userData = repository.firebaseUserMutableLiveData
    private val loggedStatus = repository.userLoggedMutableLiveData
    private val userRole = repository.userRoleMutableLiveData
    val isLoading = repository.isLoading
    val userName = repository.userNameMutableLiveData
    val nik = repository.nikMutableLiveData

    fun getUserData(): MutableLiveData<FirebaseUser?> {
        return userData
    }
    fun getLoggedStatus(): MutableLiveData<Boolean> {
        return loggedStatus
    }
    fun getUserRole(): MutableLiveData<String> {
        return userRole
    }


    fun register(email: String?, pass: String?) {
        repository.register(email, pass)
    }

    fun signIn(email: String?, pass: String?) {
        repository.login(email, pass)
    }

    fun signOut() {
        repository.signOut()
    }

}