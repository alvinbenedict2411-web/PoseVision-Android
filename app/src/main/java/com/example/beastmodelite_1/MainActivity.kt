package com.example.beastmodelite_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.beastmodelite_1.Presentation.Screens.CameraProcessLiveScreen
import com.example.beastmodelite_1.Presentation.Screens.PermissionHandlingScreen
import com.example.beastmodelite_1.Presentation.ViewModels.PermissionHandlingViewModel
import com.example.beastmodelite_1.ui.theme.BeastModeLite_1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            BeastModeLite_1Theme {
                val viewmodel: PermissionHandlingViewModel= viewModel{
                    PermissionHandlingViewModel(application)
                }
                PermissionHandlingScreen(viewmodel)
            }
        }
    }
}