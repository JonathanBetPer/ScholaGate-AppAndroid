package me.scholagate.app.nfc

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.util.Log
import me.scholagate.app.dtos.AlumnoDto
import java.io.IOException

class NfcHelper {

    fun readTag(intent: Intent): AlumnoDto? {
        val rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        if (rawMessages != null) {
            val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }
            val record = messages[0].records[0]
            val payload = record.payload

            return AlumnoDto(payload)
        }
        return null
    }

    fun writeTag(tag: Tag?, alumno: AlumnoDto): Boolean {

        val record = NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, ByteArray(0), alumno.toBytes())
        val message = NdefMessage(arrayOf(record))

        return try {
            val ndef = Ndef.get(tag)
            ndef?.connect()

            ndef?.writeNdefMessage(message)
            ndef?.close()
            true
        } catch (e: IOException) {
            Log.e("NFC", "Error writing tag", e)
            false
        }
    }

    fun formatTag(tag: Tag?, message: NdefMessage) {
        val ndefFormatable = NdefFormatable.get(tag)

        if (ndefFormatable != null) {
            try {
                ndefFormatable.connect()
                ndefFormatable.format(message)
                ndefFormatable.close()
            } catch (e: Exception) {
                Log.e("NFC", "Error writing tag", e)
            }
        } else {
            Log.e("NFC", "Tag is not formatable")
        }
    }
}