package me.scholagate.app.nfc

import android.content.ContentValues
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.util.Log
import android.widget.Toast
import me.scholagate.app.dtos.AlumnoDto
import java.io.IOException
import java.nio.charset.Charset
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class NfcManager {
    fun readTag(intent: Intent): String? {
            val rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            if (rawMessages != null) {
                val messages = rawMessages.map { it as NdefMessage }
                val payload = messages[0].records[0].payload
                val textEncoding = if (payload[0].toInt() and 128 == 0) "UTF-8" else "UTF-16"

                val languageSize = payload[0].toInt() and 63

                val result = String(payload, languageSize + 1, payload.size - languageSize - 1, Charset.forName(textEncoding))
                Log.d("NFC", "Read tag: $result")
                return result
            }

        return null
    }

    fun writeTag(tag: Tag?, text: String): Boolean {

        val languageCode = "es"

        val textBytes = text.toByteArray()
        val languageBytes = languageCode.toByteArray(Charsets.US_ASCII)

        val payload = ByteArray(1 + languageBytes.size + textBytes.size)

        payload[0] = languageBytes.size.toByte()
        System.arraycopy(languageBytes, 0, payload, 1, languageBytes.size)
        System.arraycopy(textBytes, 0, payload, 1 + languageBytes.size, textBytes.size)

        val record = NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, ByteArray(0), payload)
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
}