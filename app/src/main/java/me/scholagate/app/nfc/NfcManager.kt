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
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class NfcManager {

    fun readTag(intent: Intent): Int? {
        val rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        if (rawMessages != null) {
            val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }
            val record = messages[0].records[0]
            val payload = record.payload
            Log.i("NFC Read", "Payload id: " + payload[0].toInt())

            return payload[0].toInt()
        }
        Log.e("NFC Read", "No NFC message")
        return null
    }

    fun writeTag(tag: Tag?, alumno: AlumnoDto): Boolean {

        val alumnoBytes = alumno.id.toString().toByteArray()

        val payload = ByteArray(1 + alumnoBytes.size)

        payload[0] = alumnoBytes.size.toByte()
        System.arraycopy(alumnoBytes, 0, payload, 1, alumnoBytes.size)

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