package com.gvapps.movie.movieList.presentation.mainactivity

import com.gvapps.movie.movieList.domain.model.Movie

data class MainState(
	val isLoading: Boolean = false,
	val popularMovieListPage: Int = 1,
	val upcomingMovieListPage: Int = 1,
	val isCurrentPopularScreen: Boolean = false,
	val popularMovieList: List<Movie> = emptyList(),
	val upcomingMovieList: List<Movie> = emptyList()
)
