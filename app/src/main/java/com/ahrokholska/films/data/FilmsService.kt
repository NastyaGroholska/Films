package com.ahrokholska.films.data

import retrofit2.http.GET

interface FilmsService {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): TopRatedResponse

    data class TopRatedResponse(val page: Int, val results: List<Film>)
}