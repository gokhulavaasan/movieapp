package com.gvapps.movie.movieList.domain.repository

import com.gvapps.movie.movieList.domain.model.Movie


import com.gvapps.movie.movieList.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
	suspend fun getMovieList(
		forceFetchFromRemote: Boolean,
		category: String,
		page: Int
	): Flow<Resource<List<Movie>>>

	suspend fun getMovie(id: Int): Flow<Resource<Movie>>
}