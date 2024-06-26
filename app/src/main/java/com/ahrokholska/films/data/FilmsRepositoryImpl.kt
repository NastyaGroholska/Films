package com.ahrokholska.films.data

import com.ahrokholska.films.di.FilmsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilmsRepositoryImpl @Inject constructor(private val filmsService: FilmsService) :
    FilmsRepository {
    override suspend fun getTopRatedFilms(): Result<List<Film>> = withContext(Dispatchers.IO) {
        try {
            val response = filmsService.getTopRatedMovies()
            Result.success(response.results.map {
                it.copy(posterPath = "https://image.tmdb.org/t/p/w500${it.posterPath}")
            })
        } catch (e: Exception) {
            when (e) {
                is IOException, is HttpException -> {
                    return@withContext Result.failure(e)
                }

                else -> throw e
            }
        }
    }
}