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
                it.copy(posterPath = "$IMAGE_URL${it.posterPath}")
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

    override suspend fun getFilmDetails(filmId: Int): Result<Film> = withContext(Dispatchers.IO) {
        try {
            val response = filmsService.getMovieDetails(filmId)
            Result.success(response.copy(posterPath = "$IMAGE_URL${response.posterPath}"))
        } catch (e: Exception) {
            when (e) {
                is IOException, is HttpException -> {
                    return@withContext Result.failure(e)
                }

                else -> throw e
            }
        }
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}