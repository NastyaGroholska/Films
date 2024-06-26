package com.ahrokholska.films.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrokholska.films.data.Film
import com.ahrokholska.films.di.useCases.GetTopRatedFilmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(getTopRatedFilmsUseCase: GetTopRatedFilmsUseCase) :
    ViewModel() {
    private val _films = MutableStateFlow<Result<List<Film>>?>(null)
    val films = _films.asStateFlow()

    init {
        viewModelScope.launch {
            val result = getTopRatedFilmsUseCase()
            _films.update { result }
        }
    }
}