package me.scholagate.app.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import me.scholagate.app.R
import me.scholagate.app.components.LectorNFCAnimacion
import me.scholagate.app.components.MainTitle
import me.scholagate.app.viewModel.ScholaGateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ValidacionView(navController: NavHostController, scholaGateViewModel: ScholaGateViewModel,){

    Scaffold(
        topBar = {
            TopAppBar(title = { MainTitle(title = "Validación",
                color = MaterialTheme.colorScheme.onPrimary ) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceTint
                ),
                actions = {
                    Button(
                        onClick = {
                            navController.popBackStack()
                        }
                    ){
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                            contentDescription = "Atrás")
                    }
                }
            )
        }
    ) {
        ContentValidacion(pad = it, scholaGateViewModel = scholaGateViewModel)
    }
}


@Composable
fun ContentValidacion(pad: PaddingValues, scholaGateViewModel: ScholaGateViewModel) {
    LectorNFCAnimacion(pad)
}