package com.github.brankale.common.data.di

import android.content.Context
import com.github.brankale.common.data.getIGDBHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IGDBAuthHttpClient


@Module
@InstallIn(SingletonComponent::class)
class IGDBDataSourceProvider {

    @Provides
    @IGDBAuthHttpClient
    fun provideIGDBHttpClient(@ApplicationContext context: Context): HttpClient {
        return getIGDBHttpClient(context)
    }

}