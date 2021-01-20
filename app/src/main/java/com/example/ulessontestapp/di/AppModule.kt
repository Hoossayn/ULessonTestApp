package com.example.ulessontestapp.di

import android.content.Context
import com.example.ulessontestapp.BuildConfig
import com.example.ulessontestapp.BuildConfig.BASE_URL
import com.example.ulessontestapp.data.sources.local.SubjectLocalDataSource
import com.example.ulessontestapp.data.sources.local.SubjectLocalDataSourceImpl
import com.example.ulessontestapp.data.sources.local.ULessonDatabase
import com.example.ulessontestapp.data.sources.local.dao.RecentlyWatchedDao
import com.example.ulessontestapp.data.sources.local.dao.SubjectDao
import com.example.ulessontestapp.data.sources.remote.SubjectRemoteDataSource
import com.example.ulessontestapp.data.sources.remote.SubjectRemoteDataSourceImpl
import com.example.ulessontestapp.data.sources.remote.SubjectService
import com.example.ulessontestapp.data.sources.repository.DefaultRepository
import com.example.ulessontestapp.data.sources.repository.Repository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class AppModule {
    @Provides
    @Singleton
    fun providesSubjectDao(@ApplicationContext context: Context): SubjectDao {
        return ULessonDatabase.getDatabase(context).subjectDao()
    }

    @Provides
    @Singleton
    fun providesRecentWatchDao(@ApplicationContext context: Context): RecentlyWatchedDao {
        return ULessonDatabase.getDatabase(context).recentWatchDao()
    }

    @Provides
    @Singleton
    fun providesRepository(
        remoteSource: SubjectRemoteDataSource,
        localSource: SubjectLocalDataSource
    ): Repository {
        return DefaultRepository(remoteSource, localSource)
    }

    @Provides
    @Singleton
    fun providesRemoteDataSource(service: SubjectService): SubjectRemoteDataSource {
        return SubjectRemoteDataSourceImpl(service)
    }

    @Provides
    @Singleton
    fun providesSubjectService(retrofit: Retrofit): SubjectService {
        return retrofit.create(SubjectService::class.java)
    }

    @Singleton
    @Provides
    fun providesLocalDataSource(
        subjectDao: SubjectDao, recentlyWatchedDao: RecentlyWatchedDao
    ): SubjectLocalDataSource {
        return SubjectLocalDataSourceImpl(subjectDao, recentlyWatchedDao)
    }

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(interceptor)
        }

        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .client(builder.build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .build()
    }

}