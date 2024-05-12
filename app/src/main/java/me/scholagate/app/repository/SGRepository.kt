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
        try {
            val responseApi = api.login( Credenciales(email, password) )

            if (responseApi.isSuccessful){
                Log.d("SGRepository - Login","Success fetching data ${responseApi.body()?.token}")
                return responseApi.body()?.token
            }
            else{
                Log.e("SGRepository - Login","Error fetching data: ${responseApi.message()}")
            }

        } catch (e: Exception) {
            Log.e("SGRepository - Login","Exception fetching data:")
            e.printStackTrace()
        }
        return null
    }

    suspend fun getUsurarioActual(token: String): UsuarioDto? {

        val response = api.getUsuario( token )

        if (response.isSuccessful) {

            return response.body()
        } else {
            // Log error
            Log.e("SGRepository", "getUsuario: ${response.message()}")
        }
        return null
    }

    suspend fun getAlumno(token: String, id: Int): AlumnoDto? {

        val response = api.getAlumno(token, id)

        if (response.isSuccessful) {
            return response.body()
        } else {
            // Log error
            Log.e("SGRepository", "getAlumno: ${response.message()}")
        }
        return null
    }

    suspend fun getAlumnos(token: String): List<AlumnoDto>? {

        val response = api.getAlumnos(token)

        if (response.isSuccessful) {
            return response.body()
        } else {
            // Log error
            Log.e("SGRepository", "getAlumnos: ${response.message()}")
        }
        return null
    }


    suspend fun postReporte(token: String, reporteDto: ReporteDto): ReporteDto? {

        val response = api.postReporte(token, reporteDto)

        if (response.isSuccessful) {
            return response.body()
        } else {
            // Log error
            Log.e("SGRepository", "postReporte: ${response.message()}")
        }
        return null
    }

    suspend fun postAdjunto(token: String, adjuntoDto: AdjuntoDto): AdjuntoDto? {

        val response = api.postAdjunto(token, adjuntoDto)

        if (response.isSuccessful) {
            return response.body()
        } else {
            // Log error
            Log.e("SGRepository", "postAdjunto: ${response.message()}")
        }
        return null
    }


}