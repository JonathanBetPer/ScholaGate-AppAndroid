package me.scholagate.app

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import me.scholagate.app.dtos.AlumnoDto
import me.scholagate.app.navigation.NavManager
import me.scholagate.app.nfc.NfcManager
import me.scholagate.app.states.NFCState
import me.scholagate.app.ui.theme.ScholaGateTheme
import me.scholagate.app.viewModel.ScholaGateViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var nfcAdapter: NfcAdapter
    private val nfcManager = NfcManager()
    private val scholaGateViewModel: ScholaGateViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        Log.e("NFC", "onCreate")
        setContent {
            ScholaGateTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavManager( scholaGateViewModel)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        when (scholaGateViewModel.uiNfcViewState.value.NFCState) {
            NFCState.None -> {
                nfcAdapter.disableForegroundDispatch(this)
            }


            NFCState.Loading -> {
                Log.e("NFC", "Loading")
                Toast.makeText(this, "No hay nada que leer ni escribir", Toast.LENGTH_SHORT).show()
            }


            is NFCState.Error -> {
                Log.e("NFC", "Error")
                val mensaje = (scholaGateViewModel.uiNfcViewState.value.NFCState as NFCState.Error).message
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            }

            is NFCState.ReadyToRead -> {
                Log.e("NFC", "Ready to read")

                val idAlumno = nfcManager.readTag(intent)

                if (idAlumno != null) {
                    scholaGateViewModel.uiNfcViewState.value.copy(
                        NFCState = NFCState.SuccessRead(idAlumno),
                        alumno = scholaGateViewModel.getAlumno(idAlumno)
                    )
                } else {
                    scholaGateViewModel.uiNfcViewState.value.copy(
                        NFCState = NFCState.Error("Error Leyendo NFC"),
                        alumno = AlumnoDto()
                    )
                }
            }

            is NFCState.ReadyToWrite -> {

                Log.e("NFC", "Ready to write")

                if (intent.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {


                }else if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action){
                    val tag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)
                    } else {
                        intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
                    }

                    val alumno = (scholaGateViewModel.uiNfcViewState.value.NFCState as NFCState.ReadyToWrite).alumno

                    if (nfcManager.writeTag(tag, alumno))  Log.e("NFC TECH_DISCOVERED", "Tag written")
                }

            }

            is NFCState.SuccessWrite -> {
                Log.e("NFC", "SuccessWrite")

                Toast.makeText(this, "Tag Escrita", Toast.LENGTH_SHORT).show()
            }

            is NFCState.SuccessRead -> {
                Log.e("NFC", "SuccessRead")
            }

        }
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        val techLists = arrayOf(arrayOf("android.nfc.tech.NfcA"))
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, arrayOf(intentFilter), techLists)
    }

    private val pendingIntent: PendingIntent by lazy {
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        PendingIntent.getActivity(
            this,
            0,
            intent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.FLAG_MUTABLE
            } else {
                0
            }
        )
    }
    override fun onPause() {
        super.onPause()
        nfcAdapter.disableForegroundDispatch(this)
    }
}