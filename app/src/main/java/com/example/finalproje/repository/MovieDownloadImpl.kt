package com.example.finalproje.repository

import com.example.finalproje.model.MovieModel
import com.example.finalproje.model.SearchModel
import com.example.finalproje.service.MovieAPI
import com.example.finalproje.util.Resource

class MovieDownloadImpl (private val api: MovieAPI) : MovieDownload {
    override suspend fun downloadMovies(): Resource<SearchModel> {
        return try {
            val response = api.getData("heat")
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }
            else
            {
                Resource.error("Error",null)
            }
        }catch (e:Exception){
            Resource.error("No data!",null)
        }
    }
}