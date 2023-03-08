package com.atilsamancioglu.koinretrofit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproje.repository.MovieDownload
import com.example.finalproje.model.MovieModel
import com.example.finalproje.model.SearchModel
import com.example.finalproje.util.Resource
import kotlinx.coroutines.*

class MovieViewModel(
    private val movieDownloadRepository : MovieDownload
) : ViewModel() {

    val movieList = MutableLiveData<Resource<SearchModel>>()
    val movieError = MutableLiveData<Resource<Boolean>>()
    val movieLoading = MutableLiveData<Resource<Boolean>>()
    private var job : Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
        movieError.value = Resource.error(throwable.localizedMessage ?: "error!",data = true)
    }




    fun getDataFromAPI() {
        movieLoading.value = Resource.loading(true)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val resource = movieDownloadRepository.downloadMovies()
            withContext(Dispatchers.Main) {
                resource.data?.let {
                    movieLoading.value = Resource.loading(false)
                    movieError.value = Resource.error("",data = false)
                    movieList.value = resource
                }
            }
        }

        /*
       val BASE_URL = "https://raw.githubusercontent.com/"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoAPI::class.java)



        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.getData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    cryptoError.value = false
                    cryptoLoading.value = false
                    response.body()?.let {
                        cryptoList.value = it
                        }
                    }
                }
            }
        }

        */
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}



