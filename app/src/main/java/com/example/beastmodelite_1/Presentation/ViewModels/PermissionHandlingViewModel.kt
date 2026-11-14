package com.example.beastmodelite_1.Presentation.ViewModels

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.application

class PermissionHandlingViewModel(application: Application): AndroidViewModel(application) {
    var allpermissions=arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    )
        private set

    var showdialog by mutableStateOf<Boolean>(false)
        private set

    var permissionsstatus by mutableStateOf(false)
        private set


    fun CheckGranted(): Boolean{
        return allpermissions.all { it->
            ContextCompat.checkSelfPermission(
                application,
                it
            )== PackageManager.PERMISSION_GRANTED
        }
    }

    fun changepermissionstate(value: Boolean){
        permissionsstatus=value
    }

    fun changedialogstate(value: Boolean){
        showdialog=value
    }
}