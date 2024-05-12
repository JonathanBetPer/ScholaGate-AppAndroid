package me.scholagate.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import me.scholagate.app.ui.theme.SgGris


@Composable
fun ShowLoading(){
    Dialog(
        onDismissRequest = { var showDialog = false },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment= Center,
            modifier = Modifier
                .size(100.dp)
                .background(
                    MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}


@Composable
fun LoadingApp(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SgGris),
        contentAlignment = Center
    ) {
        IconImagotipo()
    }
}

@Composable
fun DialogPregunta(pregunta: String){

    Dialog(onDismissRequest = { /*TODO*/ },
        ) {

    }
}