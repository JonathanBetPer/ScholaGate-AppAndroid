package me.scholagate.app.network

import kotlinx.coroutines.flow.Flow
import me.scholagate.app.utils.NetworkStatus

interface NetworkConnectivityService {
    val networkStatus: Flow<NetworkStatus>
}
