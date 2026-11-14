package com.example.beastmodelite_1.Presentation.Screens

import android.util.Log
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beastmodelite_1.Presentation.ViewModels.CameraProcessViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import com.google.mlkit.vision.pose.internal.PoseDetectorImpl
import java.util.concurrent.Executors

@OptIn(ExperimentalGetImage::class)
@Composable
fun CameraProcessLiveScreen(viewModel: CameraProcessViewModel= viewModel()) {

    val context= LocalContext.current

    val lifecycleowner= LocalLifecycleOwner.current

    val preview_view=remember{
        PreviewView(context)
    }

    val executor=remember {
        Executors.newSingleThreadExecutor()
    }

    val futurecameraprovider=remember {
        ProcessCameraProvider.getInstance(context)
    }

    val image_Analysis=remember {
        ImageAnalysis.Builder().setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build()
    }

    val options=remember {
        PoseDetectorOptions.Builder()
            .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
            .setPreferredHardwareConfigs(PoseDetectorOptions.CPU_GPU)
            .build()
    }

    val Posedetectormodel=remember {
        PoseDetection.getClient(options)
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding->
        Box(modifier = Modifier.fillMaxSize()
            .padding(innerpadding)){
            AndroidView(factory = {
                preview_view.apply {
                    scaleType= PreviewView.ScaleType.FILL_CENTER
                }
            },
                modifier = Modifier.fillMaxSize())
            IconButton(onClick = {
                viewModel.changecamera()
            },
                modifier = Modifier.safeContentPadding()
                    .align(Alignment.TopEnd)) {
                Icon(imageVector = Icons.Default.Cameraswitch,
                    null,
                    tint = Color.White)
            }
            if(viewModel.posestored!=null && viewModel.scale!=null && viewModel.horizontaloffset!=null && viewModel.verticaloffset!=null) {
                Canvas(modifier = Modifier.fillMaxSize(),
                    )
                {
                    val allposes=viewModel.posestored!!.allPoseLandmarks
                    allposes.forEach { singlepose->
                        //for back-camera
                        val Orginalx=singlepose.position.x*viewModel.scale!!-viewModel.horizontaloffset!!
                        val x=if(viewModel.cameraselector== CameraSelector.DEFAULT_FRONT_CAMERA){
                            preview_view.width-Orginalx
                        }else{
                            Orginalx
                        }
                        //used this if stat for front camera case it is used for mirroring
                        val y = singlepose.position.y*viewModel.scale!!-viewModel.verticaloffset!!
                        drawCircle(
                            color=Color.Green,
                            radius = 7f,
                            center = Offset(x,y)
                        )
                    }
                }
            }
        }
    }

    image_Analysis.setAnalyzer(
        executor, ImageAnalysis.Analyzer{imageProxy ->
            val img=imageProxy.image
            if(img!=null){
                val rotationdegress=imageProxy.imageInfo.rotationDegrees
                val inputimage= InputImage.fromMediaImage(img,rotationdegress)
                Posedetectormodel.process(inputimage).addOnSuccessListener { pose->
                    val height=if(rotationdegress==90 || rotationdegress==270) img.width else img.height
                    val width=if(rotationdegress==90 || rotationdegress==270) img.height else img.width
                    val scale=maxOf(
                        preview_view.width/width.toFloat(),
                        preview_view.height/height.toFloat()
                    )
                    val scale_width=width*scale
                    val scale_height=height*scale
                    val verticaloffset=(scale_height-preview_view.height)/2f
                    val horizontaloffset=(scale_width-preview_view.width)/2f
                    viewModel.updatevalues(scale,horizontaloffset,verticaloffset)
                    viewModel.updatepose(pose)
                }.addOnFailureListener {
                    println(it)
                }.addOnCompleteListener {
                    imageProxy.close()
                }
            }
            else{
                imageProxy.close()
            }
        }
    )

    DisposableEffect(lifecycleowner,viewModel.cameraselector) {
        futurecameraprovider.addListener({
            val cameraprovider=futurecameraprovider.get()
            cameraprovider.unbindAll()
            cameraprovider.bindToLifecycle(
                lifecycleowner,
                viewModel.cameraselector,
                viewModel.preview,
                image_Analysis
            )
            viewModel.preview.surfaceProvider=preview_view.surfaceProvider
        }, ContextCompat.getMainExecutor(context))
        onDispose {
            futurecameraprovider.get().unbindAll()
        }
    }
}