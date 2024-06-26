package com.ahrokholska.films.di.useCases

import com.ahrokholska.films.di.FilmsRepository
import javax.inject.Inject

class GetTopRatedFilmsUseCase @Inject constructor(private val filmsRepository: FilmsRepository) {
    suspend operator fun invoke() = filmsRepository.getTopRatedFilms()
}