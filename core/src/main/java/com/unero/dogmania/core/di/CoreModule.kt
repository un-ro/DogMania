package com.unero.dogmania.core.di

import androidx.room.Room
import com.unero.dogmania.core.data.Repository
import com.unero.dogmania.core.data.source.local.LocalDataSource
import com.unero.dogmania.core.data.source.local.room.AppDatabase
import com.unero.dogmania.core.data.source.remote.RemoteDataSource
import com.unero.dogmania.core.data.source.remote.network.Endpoint
import com.unero.dogmania.core.domain.repository.IRepository
import com.unero.dogmania.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val roomModule = module {
    factory { get<AppDatabase>().favoriteDao() }
    single {
        val pass: ByteArray = SQLiteDatabase.getBytes("pulosari".toCharArray())
        val factory = SupportFactory(pass)
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "Dog.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val retrofitModule = module {
    single {
        val hostname = "dog.ceo"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/342F7iraaQiyxO39BjpKsHBAgbtoHigHctqQoAsnO1A=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(Endpoint::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IRepository> {
        Repository(get(), get(), get())
    }
}