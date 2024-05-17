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
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import me.scholagate.app.datastore.StoreCredenciales
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

        setContent {

            ScholaGateTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    NavManager(scholaGateViewModel)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        when (scholaGateViewModel.uiNfcViewState.value.NFCState) {
            NFCState.None -> {

            }

            NFCState.Loading -> {
                Log.i("NFC", "Loading")
                Toast.makeText(this, "No hay nada que leer ni escribir", Toast.LENGTH_SHORT).show()
            }

            NFCState.ReadyToRead -> {
                Log.i("NFC", "Ready to read")

                val idAlumno = nfcManager.readTag(intent)
                Log.i("NFC", "idAlumno: $idAlumno")
                if (idAlumno != null) {
                    scholaGateViewModel.updateNFCState(
                        scholaGateViewModel.uiNfcViewState.value.copy(
                           // NFCState = NFCState.SuccessRead(idAlumno),
                        )
                    )

                    scholaGateViewModel.onValueIdAlumno(idAlumno.toInt())

                } else {
                    scholaGateViewModel.updateNFCState(
                        scholaGateViewModel.uiNfcViewState.value.copy(
                            NFCState = NFCState.Error("Error Leyendo NFC")
                        )
                    )
                }
            }

            NFCState.SuccessWrite -> {
                Log.i("NFC", "SuccessWrite")

                Toast.makeText(this, "NFC Tag Escrita", Toast.LENGTH_SHORT).show()
            }

            is NFCState.ReadyToWrite -> {

                Log.i("NFC", "Ready to write")

                if (NfcAdapter.ACTION_TECH_DISCOVERED == intent.action){
                    val tag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)
                    } else {
                        intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
                    }

                    val alumno = (scholaGateViewModel.uiNfcViewState.value.NFCState as NFCState.ReadyToWrite).alumno

                    if (nfcManager.writeTag(tag, alumno.id.toString())){
                        scholaGateViewModel.updateNFCState(
                            scholaGateViewModel.uiNfcViewState.value.copy(
                                NFCState = NFCState.SuccessWrite
                            )
                        )
                    }
                }
            }

            is NFCState.SuccessRead -> {
                Log.i("NFC", "SuccessRead")
            }

            is NFCState.Error -> {
                val mensaje = (scholaGateViewModel.uiNfcViewState.value.NFCState as NFCState.Error).message
                Log.e("NFC", "Error $mensaje")
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
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