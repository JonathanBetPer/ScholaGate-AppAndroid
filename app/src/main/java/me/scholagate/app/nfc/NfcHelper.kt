package me.scholagate.app.nfc

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import me.scholagate.app.dtos.AlumnoDto

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

    fun writeTag(tag: Tag?, alumno: AlumnoDto) {
        val data = alumno.toBytes()
        val ndef = Ndef.get(tag)
        val ndefRecord = NdefRecord.createMime("application/vnd.me.scholagate.app", data)
        val ndefMessage = NdefMessage(arrayOf(ndefRecord))

        ndef?.let {
            it.connect()
            it.writeNdefMessage(ndefMessage)
            it.close()
        }
    }
}