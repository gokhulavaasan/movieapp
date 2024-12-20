package com.gvapps.movie.movieList.domain.usecases

import com.gvapps.movie.movieList.domain.model.Movie
import com.gvapps.movie.movieList.domain.repository.MovieRepository
import com.gvapps.movie.movieList.util.Resource
import kotlinx.coroutines.flow.Flow


class DetailUseCases(
	val movieRepository: MovieRepository
) {
	suspend operator fun invoke(
		movieId: Int
	): Flow<Resource<Movie>> {
		return movieRepository.getMovie(movieId)
	}
}