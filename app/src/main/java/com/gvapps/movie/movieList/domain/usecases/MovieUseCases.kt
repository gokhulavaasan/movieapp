package com.gvapps.movie.movieList.domain.usecases

data class MovieUseCases(
	val categoryMovieUseCases: CategoryMovieUseCases,
	val detailUseCases: DetailUseCases
)