package com.example.beastmodelite_1.Presentation.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.beastmodelite_1.Presentation.ViewModels.PermissionHandlingViewModel

class PermissionHandlingViewModelFactory(val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PermissionHandlingViewModel::class.java)){
            return PermissionHandlingViewModel(application)as T
        }
        else{
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}