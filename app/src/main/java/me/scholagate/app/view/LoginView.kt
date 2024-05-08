package me.scholagate.app.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import me.scholagate.app.StoreCredenciales
import me.scholagate.app.components.MainTitle
import me.scholagate.app.viewModel.ScholaGateViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    navController: NavHostController,
    scholaGateViewModel: ScholaGateViewModel,
    storeCredenciales: StoreCredenciales,


    ){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(title = { MainTitle(title = "Test Login", color = MaterialTheme.colorScheme.onPrimary ) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceTint
                ),
                actions = {
                }
            )

        },
        floatingActionButton = {

        }
    ) {
        ContentLogin(pad = it)
    }
}



@Composable
fun ContentLogin(pad: PaddingValues) {

}
