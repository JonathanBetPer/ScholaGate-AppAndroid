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

/**
 * Clase para manejar el almacenamiento de las credenciales del usuario.
 *
 * @property context Contexto de la aplicación.
 */
class StoreCredenciales
@Inject constructor (private val context: Context){

    companion object {
        // DataStore para almacenar las preferencias del usuario.
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("SG_Credenciales")
        // Claves para almacenar el email y la contraseña del usuario.
        val EMAIL = stringPreferencesKey("email")
        val PASSWORD = stringPreferencesKey("password")
    }

    /**
     * Obtiene las credenciales del usuario.
     *
     * @return Flow que emite las credenciales del usuario.
     */
    val getCredenciales: Flow<CredencialesDto> = context.dataStore.data.map { preferences->
                CredencialesDto(preferences[EMAIL]?:"",preferences[PASSWORD]?:"")
            }

    /**
     * Guarda el email del usuario en las preferencias.
     *
     * @param email El email del usuario.
     */
    private suspend fun saveEmail(email:String){
        context.dataStore.edit { preferences ->
            preferences[EMAIL]=email }
    }

    /**
     * Guarda la contraseña del usuario en las preferencias.
     *
     * @param password La contraseña del usuario.
     */
    private suspend fun savePassword(password:String){
        context.dataStore.edit { preferences ->
            preferences[PASSWORD]=password }
    }

    /**
     * Guarda las credenciales del usuario en las preferencias.
     *
     * @param credeciales Las credenciales del usuario.
     */
    suspend fun guardarCredenciales(credeciales: CredencialesDto) {
        saveEmail(credeciales.nombreUsuario)
        savePassword(credeciales.password)
    }

    /**
     * Borra las credenciales del usuario de las preferencias.
     */
    suspend fun borrarCredenciales(){
        saveEmail("")
        savePassword("")
    }
}