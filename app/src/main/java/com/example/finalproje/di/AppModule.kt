package com.atilsamancioglu.koinretrofit.di

import com.atilsamancioglu.koinretrofit.viewmodel.MovieViewModel
import com.example.finalproje.repository.MovieDownload
import com.example.finalproje.repository.MovieDownloadImpl
import com.example.finalproje.service.MovieAPI
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule  = module {

    //creates a singleton
    single {
        val BASE_URL = "http://www.omdbapi.com/"
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

    single<MovieDownload> {
        //since we defined retrofit above, this repository will asks for retrofit and we can simply
        //say get() in order to inject it even here
        MovieDownloadImpl(get())

        //since we are injecting the abstraction, we should explicitly state the
        //implementation and abstraction here
    }

    viewModel{
        //since we defined repo above, we can call get() here as well
        MovieViewModel(get())
    }

    //creates a factory, everytime we inject a new instance is created.
    factory {

    }
}