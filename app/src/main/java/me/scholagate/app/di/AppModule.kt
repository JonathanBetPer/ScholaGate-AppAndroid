package me.scholagate.app.di

import android.content.Context
import me.scholagate.app.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.scholagate.app.data.ScholaGateAPI
import me.scholagate.app.datastore.StoreCredenciales
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * Módulo de Dagger Hilt que provee las dependencias de la aplicación.
 *
 * @since 18/05/2024
 * @version 1.0
 * @autor JonathanBetPer
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Proporciona una instancia de Retrofit con todas las configuraciones necesarias.
     * Esta instancia se proporciona como un Singleton en el ámbito de la aplicación.
     *
     * @return Retrofit La instancia de Retrofit.
     */
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        // Crear un TrustManager que no valide los certificados SSL.
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        val sslSocketFactory = sslContext.socketFactory

        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { _, _ -> true }

        val okHttpClient = builder.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Proporciona una instancia de ScholaGateAPI.
     * Esta instancia se proporciona como un Singleton en el ámbito de la aplicación.
     *
     * @param retrofit La instancia de Retrofit.
     * @return ScholaGateAPI La instancia de ScholaGateAPI.
     */
    @Singleton
    @Provides
    fun provideScholaGateAPI(retrofit: Retrofit): ScholaGateAPI {
        return retrofit.create(ScholaGateAPI::class.java)
    }


    /**
     * Proporciona una instancia de StoreCredenciales.
     * Esta instancia se proporciona como un Singleton en el ámbito de la aplicación.
     *
     * @param context El contexto de la aplicación.
     * @return StoreCredenciales La instancia de StoreCredenciales.
     */
    @Singleton
    @Provides
    fun provideStoreCredenciales(@ApplicationContext context: Context): StoreCredenciales {
        return  StoreCredenciales(context);
    }
}