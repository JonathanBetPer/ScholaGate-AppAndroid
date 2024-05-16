package me.scholagate.app.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.scholagate.app.dtos.CredencialesDto
import javax.inject.Inject

class StoreCredenciales
@Inject constructor (private val context: Context){

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("SG_Credenciales")
        val EMAIL = stringPreferencesKey("email")
        val PASSWORD = stringPreferencesKey("password")
    }

    val getCredenciales: Flow<CredencialesDto> = context.dataStore.data.map { preferences->
                CredencialesDto(preferences[EMAIL]?:"",preferences[PASSWORD]?:"")
            }

    private suspend fun saveEmail(email:String){
        context.dataStore.edit { preferences ->
            preferences[EMAIL]=email }
    }

    private suspend fun savePassword(password:String){
        context.dataStore.edit { preferences ->
            preferences[PASSWORD]=password }
    }

    suspend fun guardarCredenciales(credeciales: CredencialesDto) {
        saveEmail(credeciales.nombreUsuario)
        savePassword(credeciales.password)
    }

    suspend fun borrarCredenciales(){
        saveEmail("")
        savePassword("")
    }
}