package me.scholagate.app.viewModel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.scholagate.app.datastore.StoreCredenciales
import me.scholagate.app.dtos.AlumnoDto
import me.scholagate.app.dtos.CredencialesDto
import me.scholagate.app.dtos.ReporteDto
import me.scholagate.app.dtos.UsuarioDto
import me.scholagate.app.repository.SGRepository
import me.scholagate.app.states.AppState
import me.scholagate.app.states.HomeState
import me.scholagate.app.states.LoginState
import me.scholagate.app.states.NFCState
import me.scholagate.app.states.UiAppState
import me.scholagate.app.states.UiHomeViewState
import me.scholagate.app.states.UiLoginViewState
import me.scholagate.app.states.UiNFCViewState
import javax.inject.Inject

/**
 * ViewModel de la aplicación ScholaGate.
 */
@HiltViewModel
class ScholaGateViewModel @Inject constructor(
    private val repository: SGRepository,
    val storeCredenciales: StoreCredenciales
    ) : ViewModel() {

    init {
        viewModelScope.launch {
            storeCredenciales.getCredenciales.collect { credenciales ->

                if (credenciales.nombreUsuario.isNotEmpty() && credenciales.password.isNotEmpty()) {

                    fetchLogin(credenciales.nombreUsuario, credenciales.password)

                    fetchUsuario()

                    _uiAppState.value = UiAppState(
                        AppState.Success(true),
                        credenciales
                    )
                }else {
                    _uiAppState.value = UiAppState(
                        AppState.Success(false),
                        credenciales
                    )
                }
            }
        }
    }

    private val _uiAppState = MutableStateFlow(
        UiAppState(
            AppState.Loading,
            CredencialesDto(),
        )
    )
    val uiAppState = _uiAppState.asStateFlow()


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
            AlumnoDto(),
            -1
        )
    )

    private val _uiHomeState = MutableStateFlow(
        UiHomeViewState(
            HomeState.None,
            UsuarioDto(),
        )
    )
    val uiHomeState = _uiHomeState.asStateFlow()

    fun updateHomeState(newHomeState: UiHomeViewState) {
        _uiHomeState.value = newHomeState
    }

    var uiNfcViewState = _uiNfcViewState.asStateFlow()

    fun updateNFCState(newNFCState: UiNFCViewState) {
        _uiNfcViewState.value = newNFCState
    }

    var _usuario by mutableStateOf(UsuarioDto())
    private set

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

    var _idAlumno by mutableIntStateOf(-1)
        private set
    fun onValueIdAlumno(value: Int) = run { _idAlumno = value }


    var _listaAlumnos by mutableStateOf(listOf<AlumnoDto>())
        private set

    var _listaGrupos by mutableStateOf(mapOf<Int, String>())
        private set




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

            while (uiLoginViewState.value.token.isEmpty()) {
                delay(1000)
            }

            updateHomeState(
                uiHomeState.value.copy(
                    homeState = HomeState.Loading
                )
            )

            val result = repository.getUsurarioActual(uiLoginViewState.value.token)

            if (result != null) {
                _usuario = result

                updateHomeState(
                    uiHomeState.value.copy(
                        homeState = HomeState.Success(result)
                    )
                )

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
                Log.e("Error", "No se pudo enviar el reporte")
            }
            return@launch
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

        viewModelScope.launch(Dispatchers.IO) {
            storeCredenciales.borrarCredenciales()
        }
    }
}








