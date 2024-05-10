package me.scholagate.app.repository

import android.util.Log
import me.scholagate.app.dtos.Credenciales
import me.scholagate.app.data.ScholaGateAPI
import me.scholagate.app.dtos.AdjuntoDto
import me.scholagate.app.dtos.AlumnoDto
import me.scholagate.app.dtos.ReporteDto
import me.scholagate.app.dtos.UsuarioDto
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

    suspend fun getUsurarioActual(token: String): UsuarioDto? {

        val response = api.getUsuario( token )

        if (response.isSuccessful) {

            return response.body()
        } else {
            // Log error
            Log.e("SGRepository", "register: ${response.errorBody().toString()}")
        }
        return null
    }

    suspend fun getAlumno(token: String, id: Int): AlumnoDto? {

        val response = api.getAlumno(token, id)

        if (response.isSuccessful) {
            return response.body()
        } else {
            // Log error
            Log.e("SGRepository", "getAlumno: ${response.errorBody().toString()}")
        }
        return null
    }

    suspend fun getAlumnos(token: String): List<AlumnoDto>? {

        val response = api.getAlumnos(token)

        if (response.isSuccessful) {
            return response.body()
        } else {
            // Log error
            Log.e("SGRepository", "getAlumnos: ${response.errorBody().toString()}")
        }
        return null
    }


    suspend fun postReporte(token: String, reporteDto: ReporteDto): ReporteDto? {

        val response = api.postReporte(token, reporteDto)

        if (response.isSuccessful) {
            return response.body()
        } else {
            // Log error
            Log.e("SGRepository", "postReporte: ${response.errorBody().toString()}")
        }
        return null
    }

    suspend fun postAdjunto(token: String, adjuntoDto: AdjuntoDto): AdjuntoDto? {

        val response = api.postAdjunto(token, adjuntoDto)

        if (response.isSuccessful) {
            return response.body()
        } else {
            // Log error
            Log.e("SGRepository", "postAdjunto: ${response.errorBody().toString()}")
        }
        return null
    }

}