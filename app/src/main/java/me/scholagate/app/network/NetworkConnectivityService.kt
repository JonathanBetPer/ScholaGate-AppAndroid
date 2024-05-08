package com.example.myapplication.network

import kotlinx.coroutines.flow.Flow
import me.scholagate.app.utils.NetworkStatus

interface NetworkConnectivityService {
    val networkStatus: Flow<NetworkStatus>
}
