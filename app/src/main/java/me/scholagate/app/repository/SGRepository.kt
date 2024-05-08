package me.scholagate.app.repository

import android.util.Log
import me.scholagate.app.model.Credenciales
import me.scholagate.app.data.ScholaGateAPI
import javax.inject.Inject

class SGRepository @Inject constructor(
    private val api: ScholaGateAPI
)   {
    suspend fun login(email: String, password: String): String? {

        val response = api.login( Credenciales(email, password) )

        if (response.isSuccessful) {
            return response.body().toString()
        } else {
            // Log error
            Log.e("SGRepository", "login: ${response.errorBody().toString()}")
        }
        return null
    }

    suspend fun getUsurarioActual(token: String): String? {

        val response = api.getUsuario( token )

        if (response.isSuccessful) {
            return response.body().toString()
        } else {
            // Log error
            Log.e("SGRepository", "register: ${response.errorBody().toString()}")
        }
        return null
    }

}