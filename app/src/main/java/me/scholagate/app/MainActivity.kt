package me.scholagate.app

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import me.scholagate.app.dtos.AlumnoDto
import me.scholagate.app.dtos.Credenciales
import me.scholagate.app.navigation.NavManager
import me.scholagate.app.nfc.NfcHelper
import me.scholagate.app.states.NFCState
import me.scholagate.app.ui.theme.ScholaGateTheme
import me.scholagate.app.viewModel.ScholaGateViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var nfcAdapter: NfcAdapter
    private val nfcHelper = NfcHelper()


    private val scholaGateViewModel: ScholaGateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
    }

    private fun createNFCIntentFilter(): Array<IntentFilter> {
        val intentFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        try {
            intentFilter.addDataType("*/*")
        } catch (e: IntentFilter.MalformedMimeTypeException) {
            throw RuntimeException("Failed to add MIME type.", e)
        }
        return arrayOf(intentFilter)
    }

    override fun onResume() {
        super.onResume()
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_IMMUTABLE
        )
        val intentFilters = createNFCIntentFilter()
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        Log.e("NFC", "onNewIntent")

        when (scholaGateViewModel.uiNfcViewState.value.NFCState) {
            NFCState.None -> {
                Log.e("NFC", "None")

            }


            NFCState.Loading -> {
                Log.e("NFC", "Loading")

            }


            is NFCState.Error -> {
                Log.e("NFC", "Error")
                val mensaje = (scholaGateViewModel.uiNfcViewState.value.NFCState as NFCState.Error).message
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            }

            is NFCState.ReadyToRead -> {
                Log.e("NFC", "Ready to read")

                val alumno = nfcHelper.readTag(intent)

                if (alumno != null) {
                    scholaGateViewModel.uiNfcViewState.value.copy(
                        NFCState = NFCState.Success(alumno),
                        alumno = alumno
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

                if (intent.action == NfcAdapter.ACTION_TAG_DISCOVERED) {

                    val tag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)
                    } else {
                        intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
                    }

                    val alumno =
                        (scholaGateViewModel.uiNfcViewState.value.NFCState as NFCState.ReadyToWrite).alumno

                    nfcHelper.writeTag(tag, alumno)
                    Log.e("NFC", "Tag written")
                }
            }

            is NFCState.Success -> {
                Log.e("NFC", "Success")

                scholaGateViewModel.uiNfcViewState.value.copy(
                    NFCState = NFCState.None,
                    alumno = AlumnoDto()
                )
            }

        }
    }
}