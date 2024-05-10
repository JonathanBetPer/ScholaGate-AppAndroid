package me.scholagate.app.di

import android.content.Context
import me.scholagate.app.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.scholagate.app.data.ScholaGateAPI
import me.scholagate.app.network.NetworkConnectivityService
import me.scholagate.app.network.NetworkConnectivityServiceImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //RETROFIT
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideScholaGateAPI(retrofit: Retrofit): ScholaGateAPI {
        return retrofit.create(ScholaGateAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkConnectivityService(@ApplicationContext context: Context): NetworkConnectivityService {
        return NetworkConnectivityServiceImpl(context)
    }
}