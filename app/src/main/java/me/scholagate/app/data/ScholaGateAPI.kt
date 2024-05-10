package me.scholagate.app.data

import me.scholagate.app.dtos.Credenciales
import me.scholagate.app.dtos.AdjuntoDto
import me.scholagate.app.dtos.AlumnoDto
import me.scholagate.app.dtos.ReporteDto
import me.scholagate.app.dtos.UsuarioDto
import me.scholagate.app.utils.Constants.Companion.ENDPOINT_ADJUNTO
import me.scholagate.app.utils.Constants.Companion.ENDPOINT_ALUMNO
import me.scholagate.app.utils.Constants.Companion.ENDPOINT_ALUMNOS
import me.scholagate.app.utils.Constants.Companion.ENDPOINT_LOGIN
import me.scholagate.app.utils.Constants.Companion.ENDPOINT_REPORTE
import me.scholagate.app.utils.Constants.Companion.ENDPOINT_USUARIO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ScholaGateAPI {

    @GET(ENDPOINT_USUARIO)
    suspend fun  getUsuario(@Header("Authorization") token: String ): Response<UsuarioDto>

    @GET("$ENDPOINT_ALUMNO{id}")
    suspend fun  getAlumno(@Header("Authorization") token: String, @Path(value="id") id: Int ): Response<AlumnoDto>

    @GET(ENDPOINT_ALUMNOS)
    suspend fun  getAlumnos(@Header("Authorization") token: String, ): Response<List<AlumnoDto>>


    @POST(ENDPOINT_LOGIN)
    suspend fun  login(@Body credenciales: Credenciales): Response<String>

    @POST(ENDPOINT_REPORTE)
    suspend fun  postReporte(@Header("Authorization") token: String, @Body reporteDto: ReporteDto): Response<ReporteDto>

    @POST(ENDPOINT_ADJUNTO)
    suspend fun  postAdjunto(@Header("Authorization") token: String, @Body adjuntoDto: AdjuntoDto): Response<AdjuntoDto>
}