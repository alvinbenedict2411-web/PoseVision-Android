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
import androidx.compose.ui.graphics.Brush
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
                    val leftShoulder = viewModel.posestored!!.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
                    val rightShoulder = viewModel.posestored!!.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
                    val leftElbow = viewModel.posestored!!.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
                    val rightElbow = viewModel.posestored!!.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
                    val leftWrist = viewModel.posestored!!.getPoseLandmark(PoseLandmark.LEFT_WRIST)
                    val rightWrist = viewModel.posestored!!.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
                    val leftHip = viewModel.posestored!!.getPoseLandmark(PoseLandmark.LEFT_HIP)
                    val rightHip = viewModel.posestored!!.getPoseLandmark(PoseLandmark.RIGHT_HIP)
                    val leftKnee = viewModel.posestored!!.getPoseLandmark(PoseLandmark.LEFT_KNEE)
                    val rightKnee = viewModel.posestored!!.getPoseLandmark(PoseLandmark.RIGHT_KNEE)
                    val leftAnkle = viewModel.posestored!!.getPoseLandmark(PoseLandmark.LEFT_ANKLE)
                    val rightAnkle = viewModel.posestored!!.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)
                    val leftPinky = viewModel.posestored!!.getPoseLandmark(PoseLandmark.LEFT_PINKY)
                    val rightPinky = viewModel.posestored!!.getPoseLandmark(PoseLandmark.RIGHT_PINKY)
                    val leftIndex = viewModel.posestored!!.getPoseLandmark(PoseLandmark.LEFT_INDEX)
                    val rightIndex = viewModel.posestored!!.getPoseLandmark(PoseLandmark.RIGHT_INDEX)
                    val leftThumb = viewModel.posestored!!.getPoseLandmark(PoseLandmark.LEFT_THUMB)
                    val rightThumb = viewModel.posestored!!.getPoseLandmark(PoseLandmark.RIGHT_THUMB)
                    val leftHeel = viewModel.posestored!!.getPoseLandmark(PoseLandmark.LEFT_HEEL)
                    val rightHeel = viewModel.posestored!!.getPoseLandmark(PoseLandmark.RIGHT_HEEL)
                    val leftFootIndex = viewModel.posestored!!.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX)
                    val rightFootIndex = viewModel.posestored!!.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX)
                    val nose = viewModel.posestored!!.getPoseLandmark(PoseLandmark.NOSE)
                    val leftEyeInner = viewModel.posestored!!.getPoseLandmark(PoseLandmark.LEFT_EYE_INNER)
                    val leftEye = viewModel.posestored!!.getPoseLandmark(PoseLandmark.LEFT_EYE)
                    val leftEyeOuter = viewModel.posestored!!.getPoseLandmark(PoseLandmark.LEFT_EYE_OUTER)
                    val rightEyeInner = viewModel.posestored!!.getPoseLandmark(PoseLandmark.RIGHT_EYE_INNER)
                    val rightEye = viewModel.posestored!!.getPoseLandmark(PoseLandmark.RIGHT_EYE)
                    val rightEyeOuter = viewModel.posestored!!.getPoseLandmark(PoseLandmark.RIGHT_EYE_OUTER)
                    val leftEar = viewModel.posestored!!.getPoseLandmark(PoseLandmark.LEFT_EAR)
                    val rightEar = viewModel.posestored!!.getPoseLandmark(PoseLandmark.RIGHT_EAR)
                    val leftMouth = viewModel.posestored!!.getPoseLandmark(PoseLandmark.LEFT_MOUTH)
                    val rightMouth = viewModel.posestored!!.getPoseLandmark(PoseLandmark.RIGHT_MOUTH)

                    fun GetUpdatedXvalue(value:Float):Float{
                        if(viewModel.cameraselector== CameraSelector.DEFAULT_FRONT_CAMERA){
                            val x=value*viewModel.scale!!-viewModel.horizontaloffset!!
                            return preview_view.width-x
                        }
                        return value*viewModel.scale!!-viewModel.horizontaloffset!!
                    }

                    fun GetUpdatedYvalue(value: Float): Float{
                        return value*viewModel.scale!!-viewModel.verticaloffset!!
                    }

                    fun DrawBone(pose1: PoseLandmark?,pose2: PoseLandmark?){
                        if(pose1!=null &&pose2!=null){
                            val x1=GetUpdatedXvalue(pose1.position.x)
                            val x2=GetUpdatedXvalue(pose2.position.x)
                            val y1=GetUpdatedYvalue(pose1.position.y)
                            val y2=GetUpdatedYvalue(pose2.position.y)
                            drawLine(
                                color = Color.Red,
                                strokeWidth = 10f,
                                start=Offset(x1,y1),
                                end = Offset(x2,y2)
                            )
                        }
                    }

                    DrawBone(leftEyeInner, leftEye)
                    DrawBone(leftEye, leftEyeOuter)

                    DrawBone(rightEyeInner, rightEye)
                    DrawBone(rightEye, rightEyeOuter)

                    DrawBone(leftEye, nose)
                    DrawBone(nose, rightEye)

                    DrawBone(leftEyeOuter, leftEar)
                    DrawBone(rightEyeOuter, rightEar)

                    DrawBone(nose, leftMouth)
                    DrawBone(leftMouth, rightMouth)
                    DrawBone(rightMouth, nose)

                    DrawBone(leftShoulder, rightShoulder)

                    DrawBone(leftShoulder, leftHip)
                    DrawBone(rightShoulder, rightHip)
                    DrawBone(leftHip, rightHip)

                    DrawBone(leftShoulder, leftElbow)
                    DrawBone(leftElbow, leftWrist)

                    DrawBone(leftWrist, leftThumb)
                    DrawBone(leftWrist, leftIndex)
                    DrawBone(leftWrist, leftPinky)

                    DrawBone(rightShoulder, rightElbow)
                    DrawBone(rightElbow, rightWrist)

                    DrawBone(rightWrist, rightThumb)
                    DrawBone(rightWrist, rightIndex)
                    DrawBone(rightWrist, rightPinky)

                    DrawBone(leftHip, leftKnee)
                    DrawBone(leftKnee, leftAnkle)
                    DrawBone(leftAnkle, leftHeel)
                    DrawBone(leftHeel, leftFootIndex)

                    DrawBone(rightHip, rightKnee)
                    DrawBone(rightKnee, rightAnkle)
                    DrawBone(rightAnkle, rightHeel)
                    DrawBone(rightHeel, rightFootIndex)

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
                            radius = 8f,
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