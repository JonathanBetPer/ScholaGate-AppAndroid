package me.scholagate.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import me.scholagate.app.navigation.NavManager
import me.scholagate.app.ui.theme.ScholaGateTheme
import me.scholagate.app.viewModel.ScholaGateViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scholaGateViewModel: ScholaGateViewModel by viewModels()

        setContent {
            ScholaGateTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavManager( scholaGateViewModel )
                }
            }
        }
    }
}