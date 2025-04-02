package com.example.apitest.di

import android.app.Application
import android.content.Context
import com.example.apitest.AppComponent
import com.example.apitest.data.local.preferences.CityPreferences
import com.example.apitest.data.local.database.AppDatabase
import com.example.apitest.data.remote.api.GeoApiService
import com.example.apitest.data.repositoryImpl.CitiesRepositoryImpl
import com.example.apitest.data.repositoryImpl.WeatherRepositoryImpl
import com.example.apitest.data.remote.api.WeatherApiService
import com.example.apitest.data.repositoryImpl.GeoRepositoryImpl
import com.example.apitest.domain.repository.CitiesRepository
import com.example.apitest.domain.repository.GeoRepository
import com.example.apitest.domain.repository.WeatherRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.openweathermap.org/"

    val contentType = "application/json".toMediaType()
    val json = Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(apiService: WeatherApiService): WeatherRepository {
        val apiKey = "8950460674bba6835a745b7b8fd5a393"
        return WeatherRepositoryImpl(apiService, apiKey)
    }

    @Provides
    @Singleton
    fun provideCitiesRepository(application: Application): CitiesRepository {
        return CitiesRepositoryImpl(
            AppDatabase.getDataBase(application).getCitiesDao(),

        )
    }

    @Provides
    @Singleton
    fun provideCityPreferences(context: Application): CityPreferences {
        return CityPreferences(context)
    }

    @Provides
    @Singleton
    fun provideGeoApiService(retrofit: Retrofit): GeoApiService {
        return retrofit.create(GeoApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGeoRepository(apiService: GeoApiService): GeoRepository {
        val apiKey = "8950460674bba6835a745b7b8fd5a393"
        return GeoRepositoryImpl(apiService, apiKey)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}
