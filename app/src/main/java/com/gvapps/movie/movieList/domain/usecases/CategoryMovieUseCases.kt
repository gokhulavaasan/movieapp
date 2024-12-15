package com.gvapps.movie.movieList.domain.usecases

import com.gvapps.movie.movieList.domain.model.Movie
import com.gvapps.movie.movieList.domain.repository.MovieRepository
import com.gvapps.movie.movieList.util.Resource
import kotlinx.coroutines.flow.Flow

class CategoryMovieUseCases(
	val movieRepository: MovieRepository
) {
	suspend operator fun invoke(
		forceFetchFromRemote: Boolean,
		category: String,
		page: Int
	): Flow<Resource<List<Movie>>> {
		return movieRepository.getMovieList(
			forceFetchFromRemote = forceFetchFromRemote,
			category = category,
			page = page
		)
	}

}