package com.example.beastmodelite_1.Presentation.Screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.beastmodelite_1.Presentation.ViewModels.PermissionHandlingViewModel

@Composable
fun PermissionHandlingScreen(viewModel: PermissionHandlingViewModel) {
    val permissionlauncher= rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ){permission->
        val permissionsisgramtedstatus=viewModel.CheckGranted()
        viewModel.changepermissionstate(permissionsisgramtedstatus)
        viewModel.changedialogstate(!permissionsisgramtedstatus)
    }
    LaunchedEffect(viewModel.permissionsstatus) {
        if(!viewModel.permissionsstatus){
            permissionlauncher.launch(
                viewModel.allpermissions
            )
        }
    }
    if(viewModel.permissionsstatus){
        //navigate
        CameraProcessLiveScreen()
    }
    if(viewModel.showdialog){
        AlertDialog(
            onDismissRequest = {
                viewModel.changedialogstate(false)
                permissionlauncher.launch(
                    viewModel.allpermissions
                )
            },
            shape = RoundedCornerShape(16.dp),
            title = {
                Text(
                    text = "Permission Required",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            },
            text = {
                Text(
                    text = "Camera and location permissions are required to use pothole detection.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.changedialogstate(false)
                        permissionlauncher.launch(
                            viewModel.allpermissions

                        )
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(

                    )
                ) {
                    Text(
                        text = "Try Again",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        viewModel.changedialogstate(false)
                        permissionlauncher.launch(
                            viewModel.allpermissions

                        )
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(

                    )
                ) {
                    Text(text = "Cancel",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black)
                }
            }
        )
    }
}
