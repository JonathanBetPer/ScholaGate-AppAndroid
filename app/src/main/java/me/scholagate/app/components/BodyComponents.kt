package me.scholagate.app.components


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import androidx.compose.ui.unit.sp

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
        minLines = 3,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .padding(bottom = 15.dp)
    )
}

