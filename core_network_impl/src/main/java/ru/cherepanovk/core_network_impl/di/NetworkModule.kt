package ru.cherepanovk.core_network_impl.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.cherepanovk.core_network_api.data.NetworkApi
import ru.cherepanovk.core_network_impl.BuildConfig
import ru.cherepanovk.core_network_impl.api.GoogleCalendarApi
import ru.cherepanovk.core_network_impl.data.NetworkApiImpl
import javax.inject.Named
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
abstract class CoreNetworkApiModule {
    @Binds
   abstract fun bindNetworkApi(networkApiImpl: NetworkApiImpl): NetworkApi
}


@Module
object NetworkModule {
    private const val LOGGING_INTERCEPTOR = "logging"

    @JvmStatic
    @Provides
    fun provideOkHttp(
        @Named(LOGGING_INTERCEPTOR) loggingInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @JvmStatic
    @Named(LOGGING_INTERCEPTOR)
    @Provides
    fun provideLoggingInterceptor(): Interceptor {
        val level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        return HttpLoggingInterceptor().setLevel(level)
    }

    @JvmStatic
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit.Builder = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())


    @JvmStatic
    @Provides
    fun provideGoogleCalendarApi(retrofitBuilder: Retrofit.Builder): GoogleCalendarApi =
        retrofitBuilder
            .baseUrl(GoogleCalendarApi.BASE_URL)
            .build()
            .create(GoogleCalendarApi::class.java)

}
