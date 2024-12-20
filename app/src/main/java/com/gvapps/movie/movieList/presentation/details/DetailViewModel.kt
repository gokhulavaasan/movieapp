package com.gvapps.movie.movieList.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvapps.movie.movieList.domain.usecases.MovieUseCases
import com.gvapps.movie.movieList.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
	private val movieUseCases: MovieUseCases,
	savedStateHandle: SavedStateHandle
) : ViewModel() {
	private val detailId = savedStateHandle.get<Int>("movieId")
	private val _detailState = MutableStateFlow(DetailState())
	val detailState = _detailState.asStateFlow()

	init {
		getMovie(detailId ?: -1)
	}

	private fun getMovie(movieId: Int) {
		viewModelScope.launch {
			_detailState.update {
				it.copy(
					isLoading = false
				)
			}
			val detailResult =
				movieUseCases.detailUseCases.invoke(movieId).collectLatest { result ->
					when (result) {
						is Resource.Error -> {
							_detailState.update {
								it.copy(
									isLoading = false
								)
							}
						}

						is Resource.Loading -> {
							_detailState.update {
								it.copy(
									isLoading = result.isLoading
								)
							}
						}

						is Resource.Success -> {
							result.data?.let { movie ->
								_detailState.update {
									it.copy(
										movie = movie
									)
								}
							}
						}
					}
				}

		}
	}

}