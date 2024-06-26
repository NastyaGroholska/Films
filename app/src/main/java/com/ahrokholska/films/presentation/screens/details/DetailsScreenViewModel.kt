package com.ahrokholska.films.presentation.screens.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.films.data.Film
import com.ahrokholska.films.di.useCases.GetFilmDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getFilmDetailsUseCase: GetFilmDetailsUseCase
) : ViewModel() {
    private val _film = MutableStateFlow<Result<Film>?>(null)
    val film = _film.asStateFlow()

    init {
        viewModelScope.launch {
            val id: Int? = savedStateHandle["id"]
            id?.let {
                val result = getFilmDetailsUseCase(id)
                _film.update { result }
            }
        }
    }
}