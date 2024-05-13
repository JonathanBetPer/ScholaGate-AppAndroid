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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.scholagate.app.dtos.AdjuntoDto
import me.scholagate.app.dtos.AlumnoDto
import me.scholagate.app.dtos.CredencialesDto
import me.scholagate.app.dtos.ReporteDto
import me.scholagate.app.dtos.UsuarioDto
import me.scholagate.app.repository.SGRepository
import me.scholagate.app.states.AppState
import me.scholagate.app.states.LoginState
import me.scholagate.app.states.NFCState
import me.scholagate.app.states.UiAppState
import me.scholagate.app.states.UiLoginViewState
import me.scholagate.app.states.UiNFCViewState
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

    private val _uiAppState = MutableStateFlow(
        UiAppState(
            AppState.Loading,
            CredencialesDto(),
        )
    )
    val uiAppState = _uiAppState.asStateFlow()

    fun updateAppState(newAppState: UiAppState) {
        _uiAppState.value = newAppState
    }

    private var _uiLoginViewState = MutableStateFlow(
        UiLoginViewState(
            LoginState.None,
            ""
        )
    )
    val uiLoginViewState = _uiLoginViewState.asStateFlow()

    private val _uiNfcViewState = MutableStateFlow(
        UiNFCViewState(
            NFCState.None,
            AlumnoDto()
        )
    )
    var uiNfcViewState = _uiNfcViewState.asStateFlow()

    var _usuario by mutableStateOf(UsuarioDto())
    private set

    fun onValueUsuario(value: UsuarioDto) = run { _usuario = _usuario.copy(
        id = value.id,
        nombre = value.nombre,
        correo = value.correo,
        rol = value.rol
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

    var _listaGrupos by mutableStateOf(mapOf<Int, String>())
        private set

    fun onValueListaGrupos(value: HashMap<Int, String>) = run { _listaGrupos = value }

    var _alumno by mutableStateOf(AlumnoDto())
        private set

    fun onValueAlumno(value: AlumnoDto) = run { _alumno = _alumno.copy(
        id = value.id,
        idGrupo = value.idGrupo,
        nombre = value.nombre,
        fechaNac = value.fechaNac,
        foto = value.foto,

    ) }

    fun loginStartApp(){

        if (uiAppState.value.credencialesDto != null) {
            fetchLogin(_uiAppState.value.credencialesDto!!.nombreUsuario,
                _uiAppState.value.credencialesDto!!.password)
        }

        _uiAppState.value = uiAppState.value.copy(
            appState = AppState.Success(true)
        )

    }

    fun fetchLogin(email: String, password: String) {

        _uiLoginViewState.value = _uiLoginViewState.value.copy(
            loginState = LoginState.Loading
        )
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.login(email.trim(), password.trim())

            if (result != null) {
                _uiLoginViewState.value = _uiLoginViewState.value.copy(
                    loginState = LoginState.Success(result),
                    token = result
                )
            } else {
                _uiLoginViewState.value = _uiLoginViewState.value.copy(
                    loginState = LoginState.Error("Error")
                )
            }
        }
    }

    fun fetchUsuario() {
        viewModelScope.launch(Dispatchers.IO) {

            val result = repository.getUsurarioActual(uiLoginViewState.value.token)
            if (result != null) {
                _usuario = result
            } else {
                Log.e("Error", "No se pudo obtener el usuario ${uiLoginViewState.value.token}")
            }
        }
    }

    fun fetchReporte(reporteDto: ReporteDto) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.postReporte(uiLoginViewState.value.token, reporteDto)
            if (result != null) {
                _reporte = result
            } else {
                Log.e("Error", "No se pudo obtener el alumno")
            }
        }
    }

    fun fetchAdjunto(adjuntoDto: AdjuntoDto) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.postAdjunto(uiLoginViewState.value.token, adjuntoDto)
            if (result != null) {
                _adjunto = result
            } else {
                Log.e("Error", "No se pudo obtener el alumno")
            }
        }
    }

    fun fetchAlumno(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAlumno(uiLoginViewState.value.token, id)
            if (result != null) {
                _alumno = result
            } else {
                Log.e("Error", "No se pudo obtener el alumno")
            }
        }
    }

    fun fetchAlumnos() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAlumnos(uiLoginViewState.value.token)
            if (result != null) {
                _listaAlumnos = result
            } else {
                Log.e("Error", "No se pudo obtener el alumno")
            }
        }
    }

    fun fetchGruposInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getGruposInfo(uiLoginViewState.value.token)
            if (result != null) {
                _listaGrupos = result
            } else {
                Log.e("Error", "No se pudo obtener el alumno")
            }
        }
    }

    fun logout() {

        _uiAppState.value = _uiAppState.value.copy(
            appState = AppState.Loading,
            credencialesDto = CredencialesDto()
        )

        _uiLoginViewState.value = _uiLoginViewState.value.copy(
            loginState = LoginState.None,
            token = ""
        )
    }
}








