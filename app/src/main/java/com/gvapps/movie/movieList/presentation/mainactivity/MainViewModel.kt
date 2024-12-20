package com.gvapps.movie.movieList.presentation.mainactivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gvapps.movie.movieList.Category
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
class MainViewModel @Inject constructor(
	private val movieUseCases: MovieUseCases
) : ViewModel() {

	private var _movieListState = MutableStateFlow(MainState())
	val movieListState = _movieListState.asStateFlow()

	init {
		getCategoryList(false, Category.POPULAR)
		getCategoryList(false, Category.UPCOMING)
	}

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
				getCategoryList(true, event.category)
			}
		}
	}

	private fun getCategoryList(fetchFromRemote: Boolean, category: String) {
		viewModelScope.launch {
			_movieListState.update {
				it.copy(
					isLoading = true
				)
			}
			val movieCategory =
				if (category == Category.POPULAR) Category.POPULAR else Category.UPCOMING
			movieUseCases.categoryMovieUseCases.invoke(
				forceFetchFromRemote = fetchFromRemote,
				category = movieCategory,
				page = movieListState.value.popularMovieListPage
			).collectLatest { result ->
				when (result) {
					is Resource.Error -> {
						_movieListState.update {
							it.copy(
								isLoading = false
							)
						}
					}

					is Resource.Loading -> {
						_movieListState.update {
							it.copy(
								isLoading = result.isLoading
							)
						}
					}

					is Resource.Success -> {
						if (category == Category.POPULAR) {
							result.data?.let { result ->
								_movieListState.update {
									it.copy(
										popularMovieList = movieListState.value.popularMovieList + result.shuffled(),
										popularMovieListPage = movieListState.value.popularMovieListPage + 1
									)

								}
							}
						} else {
							result.data?.let { result ->
								_movieListState.update {
									it.copy(
										upcomingMovieList = movieListState.value.upcomingMovieList + result.shuffled(),
										upcomingMovieListPage = movieListState.value.upcomingMovieListPage + 1
									)
								}
							}
						}

					}
				}
			}
		}
	}
}