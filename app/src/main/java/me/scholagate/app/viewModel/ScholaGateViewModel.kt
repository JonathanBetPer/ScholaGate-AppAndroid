package me.scholagate.app.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.network.NetworkConnectivityService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import me.scholagate.app.dtos.AdjuntoDto
import me.scholagate.app.dtos.ReporteDto
import me.scholagate.app.dtos.UsuarioDto
import me.scholagate.app.model.Credenciales
import me.scholagate.app.model.Usuario
import me.scholagate.app.repository.SGRepository
import me.scholagate.app.state.LoginState
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

    private val _usuario = MutableStateFlow(
        UsuarioDto(
            0,
            "",
            "",
            "",
        )
    )
    val usuario = _usuario.asStateFlow()

    private val _token = MutableStateFlow("")
    val token = _token.asStateFlow()

    private val _loginState = MutableStateFlow(
        LoginState(
            Credenciales("", "")
        )
    )
    val loginState = _loginState.asStateFlow()


    init {
        fetchCredenciales()
    }

    private fun fetchCredenciales() {
        TODO("Not yet implemented")
    }

    private fun fetchLogin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.login(email, password)
            _token.value = result ?: ""
        }
    }

    private fun fetchUsuario(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getUsurarioActual(token)
            if (result != null) {
                _usuario.value = result
            } else {
                Log.e("Error", "No se pudo obtener el usuario")
            }
        }
    }

    private fun fetchReporte(token: String, reporteDto: ReporteDto) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.postReporte(token, reporteDto)
            if (result != null) {
                //TODO: implementar
            } else {
                Log.e("Error", "No se pudo obtener el alumno")
            }
        }
    }

    private fun fetchAdjunto(token: String, adjuntoDto: AdjuntoDto) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.postAdjunto(token, adjuntoDto)
            if (result != null) {
                //TODO: implementar
            } else {
                Log.e("Error", "No se pudo obtener el alumno")
            }
        }
    }

    private fun fetchAlumno(token: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAlumno(token, id)
            if (result != null) {
                //TODO: implementar
            } else {
                Log.e("Error", "No se pudo obtener el alumno")
            }
        }
    }

    private fun fetchAlumnos(token: String) {
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








