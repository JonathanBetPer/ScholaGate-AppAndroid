package me.scholagate.app.components

import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.scholagate.app.R

@Composable
fun SpaceV(size: Dp = 5.dp){
    Spacer(modifier = Modifier.height(size))
}

@Composable
fun SpaceH(size: Dp = 5.dp){
    Spacer(modifier = Modifier.width(size))
}
@Composable
fun MainTitle(title: String, color : Color = Color.White) {
    Text(text = title, color = color, fontWeight = FontWeight.Bold)
}

@Composable
fun FloatButton(imageVector: Int, contentDescription: String, color: Color = Color.White, onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = color
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = imageVector),
            contentDescription = contentDescription
        )
    }
}
@Composable
fun MainIconButton(icon: ImageVector, description: String, tint: Color, onClick:() -> Unit){
    IconButton(onClick = onClick) {
        Icon(imageVector = icon,
            contentDescription = description,
            tint = tint)
    }
}

@Composable
fun TextFieldGenerico(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        minLines = 1,
        maxLines = 1,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .padding(bottom = 15.dp)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarLogo(){
    TopAppBar(title = { ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        actions = {
        }
    )
}

@Composable
fun CheckBoxLogIn( checked: Boolean, onCheckedChange: (Boolean) -> Unit){

}
