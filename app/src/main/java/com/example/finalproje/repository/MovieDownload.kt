package com.example.finalproje.repository

import com.example.finalproje.model.MovieModel
import com.example.finalproje.model.SearchModel
import com.example.finalproje.util.Resource

interface MovieDownload {
    suspend fun downloadMovies() : Resource<SearchModel>
}