package me.scholagate.app.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import me.scholagate.app.R
import me.scholagate.app.ui.theme.SgGris

/**
 *  Componentes de la interfaz que contienen Animaciones o están relacionados con cambios de State
 *  @since 18/05/2024
 *  @version 1.0
 *  @autor JonathanBetPer
 */
@Composable
fun ShowLoading(){
    Dialog(
        onDismissRequest = { },
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
fun LectorNFCAnimacion(pad: PaddingValues, colorBackgroud: Color = MaterialTheme.colorScheme.background){
    val alpha = remember { Animatable(0f) } // Empieza completamente transparente

    LaunchedEffect(key1 = true) {
        while (true) {
            alpha.animateTo(
                targetValue = 0.8f,
                animationSpec = tween(
                    durationMillis = 2500,
                    easing = LinearEasing
                )
            )
            alpha.animateTo(
                targetValue = 0.15f,
                animationSpec = tween(
                    durationMillis = 2500,
                    easing = LinearEasing
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .padding(pad)
            .fillMaxSize()
            .background(colorBackgroud),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.nfc_svgrepo_com),
            contentDescription = "Validación NFC",
            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = alpha.value),
            modifier = Modifier.size(200.dp)
        )
    }
}

@Composable
fun MiniNFCAnimacion(){

    val alpha = remember { Animatable(0f) } // Empieza completamente transparente

    LaunchedEffect(key1 = true) {
        while (true) {
            alpha.animateTo(
                targetValue = 0.8f,
                animationSpec = tween(
                    durationMillis = 2500, // Duración de la animación en milisegundos
                    easing = LinearEasing // Tipo de interpolación
                )
            )
            alpha.animateTo(
                targetValue = 0.15f,
                animationSpec = tween(
                    durationMillis = 2500, // Duración de la animación en milisegundos
                    easing = LinearEasing
                )
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(115.dp)
            .border(5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(25.dp))
            .clip(RoundedCornerShape(25.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
        ) {

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.nfc_svgrepo_com),
            contentDescription = "Crear Registro NFC",
            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = alpha.value),
            modifier = Modifier.size(75.dp)
        )
    }
}
