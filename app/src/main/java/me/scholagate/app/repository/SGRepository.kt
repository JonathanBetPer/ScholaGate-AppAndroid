package me.scholagate.app.repository

import android.util.Log
import me.scholagate.app.dtos.CredencialesDto
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
            val responseApi = api.login( CredencialesDto(email, password) )

            if (responseApi.isSuccessful){
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
        try {
            val responseApi =  api.getUsuario("Bearer $token")

            if (responseApi.isSuccessful){
                Log.d("SGRepository - getUser","Success fetching data, user with id: ${responseApi.body()?.id}")
                return responseApi.body()
            }
            else{
                Log.e("SGRepository - getUser","Error fetching data: ${responseApi.message()}")
            }

        } catch (e: Exception) {
            Log.e("SGRepository - getUser","Exception fetching data:")
            e.printStackTrace()
        }
        return null
    }

    suspend fun getAlumno(token: String, id: Int): AlumnoDto? {

        try {
            val responseApi =  api.getAlumno("Bearer $token", id)

            if (responseApi.isSuccessful){
                Log.d("SGRepository - getAlumno","Success fetching data, alumno with id: ${responseApi.body()?.id}")
                return responseApi.body()
            }
            else{
                Log.e("SGRepository - getAlumno","Error fetching data: ${responseApi.message()}")
            }

        } catch (e: Exception) {
            Log.e("SGRepository - getAlumno","Exception fetching data:")
            e.printStackTrace()
        }
        return null
    }

    suspend fun getAlumnos(token: String): List<AlumnoDto>? {

        try {
            val responseApi =  api.getAlumnos("Bearer $token")

            if (responseApi.isSuccessful){
                Log.d("SGRepository - getAlumnos","Success fetching data: ${responseApi.body()}")
                return responseApi.body()
            }
            else{
                Log.e("SGRepository - getAlumnos","Error fetching data: ${responseApi.message()}")
            }

        } catch (e: Exception) {
            Log.e("SGRepository - getAlumnos","Exception fetching data:")
            e.printStackTrace()
        }
        return null
    }

    suspend fun getGruposInfo(token: String): HashMap<Int, String>? {

        try {
            val responseApi =  api.getGruposInfo("Bearer $token")

            if (responseApi.isSuccessful){
                Log.d("SGRepository - getGruposInfo","Success fetching data: ${responseApi.body()}")
                return responseApi.body()
            }
            else{
                Log.e("SGRepository - getGruposInfo","Error fetching data: ${responseApi.message()}")
            }

        } catch (e: Exception) {
            Log.e("SGRepository - getGruposInfo","Exception fetching data:")
            e.printStackTrace()
        }
        return null
    }


    suspend fun postReporte(token: String, reporteDto: ReporteDto): ReporteDto? {

        try {
            val responseApi =  api.postReporte("Bearer $token", reporteDto)

            if (responseApi.isSuccessful){
                Log.d("SGRepository - postReporte","Success fetching data: ${responseApi.body()}")
                return responseApi.body()
            }
            else{
                Log.e("SGRepository - postReporte","Error fetching data: ${responseApi.message()}")
            }

        } catch (e: Exception) {
            Log.e("SGRepository - postReporte","Exception fetching data:")
            e.printStackTrace()
        }
        return null
    }

    suspend fun postAdjunto(token: String, adjuntoDto: AdjuntoDto): AdjuntoDto? {

        try {
            val responseApi =  api.postAdjunto("Bearer $token", adjuntoDto)

            if (responseApi.isSuccessful){
                Log.d("SGRepository - postAdjunto","Success fetching data: ${responseApi.body()}")
                return responseApi.body()
            }
            else{
                Log.e("SGRepository - postAdjunto","Error fetching data: ${responseApi.message()}")
            }

        } catch (e: Exception) {
            Log.e("SGRepository - postAdjunto","Exception fetching data:")
            e.printStackTrace()
        }
        return null
    }
}