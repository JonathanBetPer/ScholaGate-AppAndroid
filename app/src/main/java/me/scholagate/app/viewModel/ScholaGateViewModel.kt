package me.scholagate.app.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.NetworkConnectivityService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.scholagate.app.dtos.AdjuntoDto
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

    init {
        if (_credenciales.nombreUsuario != "" && _credenciales.password != ""){
            fetchLogin(_credenciales.nombreUsuario, _credenciales.password)
        }
    }

    fun tokenCorrecto(): Boolean{
        if (_token != "") {
            return true
        } else {
            return false
        }
    }

    fun fetchLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.login(email, password)
            _token = result ?: ""
        }
    }

    fun fetchUsuario(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getUsurarioActual(token)
            if (result != null) {
                _usuario = result
            } else {
                Log.e("Error", "No se pudo obtener el usuario")
            }
        }
    }

    fun fetchReporte(token: String, reporteDto: ReporteDto) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.postReporte(token, reporteDto)
            if (result != null) {
                //TODO: implementar
            } else {
                Log.e("Error", "No se pudo obtener el alumno")
            }
        }
    }

    fun fetchAdjunto(token: String, adjuntoDto: AdjuntoDto) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.postAdjunto(token, adjuntoDto)
            if (result != null) {
                //TODO: implementar
            } else {
                Log.e("Error", "No se pudo obtener el alumno")
            }
        }
    }

    fun fetchAlumno(token: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAlumno(token, id)
            if (result != null) {
                //TODO: implementar
            } else {
                Log.e("Error", "No se pudo obtener el alumno")
            }
        }
    }

    fun fetchAlumnos(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAlumnos(token)
            if (result != null) {
                //TODO: implementar
            } else {
                Log.e("Error", "No se pudo obtener el alumno")
            }
        }
    }
}








