package com.example.beastmodelite_1.Presentation.ViewModels

import androidx.camera.core.CameraSelector
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.pose.Pose

class CameraProcessViewModel(): ViewModel() {
    var cameraselector by mutableStateOf<CameraSelector>(CameraSelector.DEFAULT_BACK_CAMERA)
        private set

    var preview= androidx.camera.core.Preview.Builder().build()
        private set

    var posestored by mutableStateOf<Pose?>(null)
        private set

    var scale by mutableStateOf<Float?>(null)
        private set

    var horizontaloffset by mutableStateOf<Float?>(null)
        private set

    var verticaloffset by mutableStateOf<Float?>(null)
        private set

    fun changecamera() {
        if (cameraselector == CameraSelector.DEFAULT_BACK_CAMERA) {
            cameraselector = CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            cameraselector = CameraSelector.DEFAULT_BACK_CAMERA
        }
    }

    fun updatepose(pose: Pose){
        posestored=pose
    }

    fun updatevalues(inputscale:Float,inputhorizontaloffset: Float,inputverticaloffset:Float){
        scale=inputscale
        horizontaloffset=inputhorizontaloffset
        verticaloffset=inputverticaloffset
    }
}