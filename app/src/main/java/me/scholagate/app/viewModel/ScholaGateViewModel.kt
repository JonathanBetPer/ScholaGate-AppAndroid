package me.scholagate.app.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import me.scholagate.app.network.NetworkConnectivityService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.scholagate.app.dtos.AdjuntoDto
import me.scholagate.app.dtos.AlumnoDto
import me.scholagate.app.dtos.Credenciales
import me.scholagate.app.dtos.ReporteDto
import me.scholagate.app.dtos.UsuarioDto
import me.scholagate.app.repository.SGRepository
import me.scholagate.app.utils.NetworkStatus
import javax.inject.Inject

@HiltViewModel
class ScholaGateViewModel @Inject constructor(
    private val repository: SGRepository,
    networkConnectivityService: NetworkConnectivityService,
    ) : ViewModel() {

    val networkStatus: StateFlow<NetworkStatus> = networkConnectivityService.networkStatus.stateIn(
        initialValue = NetworkStatus.Unknown,
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000)
    )

    var _usuario by mutableStateOf(UsuarioDto())
    private set

    fun onValueUsuario(value: UsuarioDto) = run { _usuario = _usuario.copy(
        id = value.id,
        nombre = value.nombre,
        correo = value.correo,
        rol = value.rol
    ) }


    var _token by mutableStateOf("")
    fun onValueToken(value: String) = run { _token = value }

    var _credenciales by mutableStateOf(Credenciales())
    private set

    fun onValueCredenciales(value: Credenciales) = run { _credenciales = _credenciales.copy(
        nombreUsuario = value.nombreUsuario,
        password = value.password
    ) }

    var _reporte by mutableStateOf(ReporteDto())
        private set
    fun onValueReporte(value: ReporteDto) = run { _reporte = _reporte.copy(
        id = value.id,
        idAlumno = value.idAlumno,
        idUsuario = value.idUsuario,
        tipo = value.tipo,
        motivo = value.motivo,
        fecha = value.fecha
    ) }

    var _adjunto by mutableStateOf(AdjuntoDto())
        private set

    fun onValueAdjunto(value: AdjuntoDto) = run { _adjunto = _adjunto.copy(
        id = value.id,
        idReporte = value.idReporte,
        nombre = value.nombre,
        foto = value.foto
    ) }

    var _listaAlumnos by mutableStateOf(listOf<AlumnoDto>())
        private set

    fun onValueListaAlumnos(value: List<AlumnoDto>) = run { _listaAlumnos = value }

    var _alumno by mutableStateOf(AlumnoDto())
        private set

    fun onValueAlumno(value: AlumnoDto) = run { _alumno = _alumno.copy(
        id = value.id,
        idGrupo = value.idGrupo,
        nombre = value.nombre,
        fechaNac = value.fechaNac,
        foto = value.foto,

    ) }

    init {
        if (_credenciales.nombreUsuario != "" && _credenciales.password != ""){
            fetchLogin(_credenciales.nombreUsuario, _credenciales.password)
        }
    }

    fun tokenCorrecto(): Boolean{
        return _token != ""
    }

    fun fetchLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.login(email.trim(), password.trim())
            _token = result?:""
        }
    }

    fun fetchUsuario() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getUsurarioActual(_token)
            if (result != null) {
                _usuario = result
            } else {
                Log.e("Error", "No se pudo obtener el usuario")
            }
        }
    }

    fun fetchReporte(reporteDto: ReporteDto) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.postReporte(_token, reporteDto)
            if (result != null) {
                _reporte = result
            } else {
                Log.e("Error", "No se pudo obtener el alumno")
            }
        }
    }

    fun fetchAdjunto(adjuntoDto: AdjuntoDto) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.postAdjunto(_token, adjuntoDto)
            if (result != null) {
                _adjunto = result
            } else {
                Log.e("Error", "No se pudo obtener el alumno")
            }
        }
    }

    fun fetchAlumno(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAlumno(_token, id)
            if (result != null) {
                _alumno = result
            } else {
                Log.e("Error", "No se pudo obtener el alumno")
            }
        }
    }

    fun fetchAlumnos() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAlumnos(_token)
            if (result != null) {
                _listaAlumnos = result
            } else {
                Log.e("Error", "No se pudo obtener el alumno")
            }
        }
    }
}








