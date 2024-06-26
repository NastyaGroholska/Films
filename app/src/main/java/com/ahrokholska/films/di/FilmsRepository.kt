package com.ahrokholska.films.di

import com.ahrokholska.films.data.Film

interface FilmsRepository {
    suspend fun getTopRatedFilms(): Result<List<Film>>
}