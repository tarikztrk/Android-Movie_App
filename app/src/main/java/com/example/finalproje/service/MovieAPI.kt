package com.example.finalproje.service

import com.example.finalproje.model.MovieModel
import com.example.finalproje.model.SearchModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "b2268941"
interface MovieAPI {
    @GET(".")
    suspend fun getData(@Query("s") searchString :String,
                        @Query("apikey") apiKey :String = API_KEY ): Response<SearchModel>

    @GET("movieDetail")
    suspend fun getMovieDetail(
        @Query("i") imdbId : String,
        @Query("apikey") apiKey: String = API_KEY
    ) : Response<MovieModel>
}