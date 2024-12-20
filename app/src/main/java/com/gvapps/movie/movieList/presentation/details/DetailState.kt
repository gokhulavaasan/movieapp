package com.gvapps.movie.movieList.presentation.details

import com.gvapps.movie.movieList.domain.model.Movie

data class DetailState(
	val isLoading: Boolean = false,
	val movie: Movie? = null
)
