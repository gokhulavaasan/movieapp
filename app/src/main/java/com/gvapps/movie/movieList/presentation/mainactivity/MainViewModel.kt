package com.gvapps.movie.movieList.presentation.mainactivity

import androidx.lifecycle.ViewModel
import com.gvapps.movie.movieList.domain.usecases.MovieUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val movieUseCases: MovieUseCases
) : ViewModel() {

	private var _movieListState = MutableStateFlow(MainState())
	val movieListState = _movieListState.asStateFlow()

	fun onEvent(event: MainUiEvents) {
		when (event) {
			MainUiEvents.Navigate -> {
				_movieListState.update {
					it.copy(
						isCurrentPopularScreen = !movieListState.value.isCurrentPopularScreen
					)
				}
			}

			is MainUiEvents.Paginate -> {
				getCategoryList()
			}
		}

	}

	private fun getCategoryList() {
		TODO("Not yet implemented")
	}

}