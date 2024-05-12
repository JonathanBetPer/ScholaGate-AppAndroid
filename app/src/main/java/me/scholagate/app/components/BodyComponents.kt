package me.scholagate.app.components

import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import me.scholagate.app.R
import me.scholagate.app.dtos.AlumnoDto
import java.io.ByteArrayInputStream
import java.io.InputStream

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
fun TextFieldGenerico(value: String, onValueChange: (String) -> Unit, label: String, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = value,
        minLines = 1,
        maxLines = 1,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarLogo(){
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconImagotipo()
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceTint,
        ),
        actions = {},
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    )
}

@Composable
fun CheckBoxLogIn( checked: Boolean, onCheckedChange: (Boolean) -> Unit, modifier: Modifier = Modifier){
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.onSurface
            )
        )
        Text(text = "Recordar credenciales")
    }
}

/**
 * https://gist.github.com/stevdza-san/8f8d81935804fc5d32d924ac2e9b7cc6
 */
@Composable
fun PasswordField(password: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {


    var passwordVisibility by remember { mutableStateOf(false) }

    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.baseline_visibility_24)
    else
        painterResource(id = R.drawable.baseline_visibility_off_24)

    OutlinedTextField(
        value = password,
        onValueChange =  onValueChange,
        modifier = modifier,
        placeholder = { Text(text = "Contraseña") },
        label = { Text(text = "Contraseña") },
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                Icon(
                    painter = icon,
                    contentDescription = "Visibility Icon"
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = if (passwordVisibility) VisualTransformation.None
        else PasswordVisualTransformation()
    )
}

@Composable
fun BotonPrincipalIcon(idIcon: Int, description: String, enabled: Boolean? = true, onClick: () -> Unit){


    OutlinedIconButton(
        onClick = onClick,
        modifier = Modifier
            .size(200.dp)
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surfaceTint),
        enabled = enabled ?: true,
        shape = RoundedCornerShape(25.dp),
    ) {

        Icon(
            painter = painterResource(id = idIcon),
            contentDescription = description,
            tint = Color.Unspecified,
            modifier = Modifier
                .padding(15.dp)
                .size(1055.dp)
        )
    }
}

@Composable
fun IconImagotipo(){
    Icon(
        imageVector = ImageVector.vectorResource(id = R.drawable.scholagate_imagotipo),
        contentDescription = "Imagotipo ScholaGate",
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .fillMaxHeight()
        ,  tint = Color.Unspecified
    )
}

@Composable
fun CardAlumno(alumno: AlumnoDto, onClick: () -> Unit){

    Column(
        modifier = Modifier
            .clickable(
                enabled = true,
                onClick = onClick
            )
            .padding(50.dp)
            .fillMaxSize()
            .background(Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        /*
        val inputStream: InputStream = ByteArrayInputStream(alumno.foto.toByteArray())
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val imageBitmap = bitmap.asImageBitmap()

        Image(
            bitmap = imageBitmap,
            contentDescription = "Foto del alumno",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
*/
        Text(text = alumno.nombre, fontWeight = FontWeight.Bold)
        Text(text = alumno.fechaNac)

    }
}



@Composable
fun MiniCardAlumno(alumno: AlumnoDto, onClick: () -> Unit) {

    Row(
        modifier = Modifier
            .clickable(
                enabled = true,
                onClick = onClick)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceTint)
            .padding(8.dp),

    ) {
        Text(text = alumno.nombre, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = alumno.fechaNac.toString())
    }
}

@Composable
fun Buscador(texto: MutableState<String>){

    OutlinedTextField(
        value = texto.value,
        onValueChange = {texto.value = it },
        label = { Text(text = "Buscador") },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .padding(8.dp)
    )
}
@Composable
fun AlertDialogPersonalizado(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Sí")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("No")
            }
        }
    )
}