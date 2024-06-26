package com.ahrokholska.films.di.useCases

import com.ahrokholska.films.di.FilmsRepository
import javax.inject.Inject

class GetFilmDetailsUseCase @Inject constructor(private val filmsRepository: FilmsRepository) {
    suspend operator fun invoke(filmId: Int) = filmsRepository.getFilmDetails(filmId)
}