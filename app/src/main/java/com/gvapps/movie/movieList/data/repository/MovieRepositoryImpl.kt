package com.gvapps.movie.movieList.data.repository

import coil.network.HttpException
import com.gvapps.movie.movieList.data.local.MovieDao
import com.gvapps.movie.movieList.data.mappers.toMovie
import com.gvapps.movie.movieList.data.mappers.toMovieEntity
import com.gvapps.movie.movieList.data.remote.MovieApi
import com.gvapps.movie.movieList.domain.model.Movie
import com.gvapps.movie.movieList.domain.repository.MovieRepository
import com.gvapps.movie.movieList.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
	val movieDao: MovieDao,
	val movieApi: MovieApi
) : MovieRepository {
	override suspend fun getMovieList(
		forceFetchFromRemote: Boolean,
		category: String,
		page: Int
	): Flow<Resource<List<Movie>>> {
		return flow {
			emit(Resource.Loading(isLoading = true))
			val localMovieList = movieDao.getMovieListByCategory(category)
			val loadLocalMovie = localMovieList.isNotEmpty() && !forceFetchFromRemote
			if (loadLocalMovie) {
				emit(
					Resource.Success(
						data = localMovieList.map { movie ->
							movie.toMovie(category)
						})
				)
				emit(Resource.Loading(false))
				return@flow
			}
			val movieListFromApi = try {
				movieApi.getMoviesList(
					category = category,
					page = page,
				)
			} catch (e: HttpException) {
				e.printStackTrace()
				emit(Resource.Error(message = "Error Loading Movies"))
				return@flow
			} catch (e: IOException) {
				e.printStackTrace()
				emit(Resource.Error(message = "Error Loading Movies"))
				return@flow
			} catch (e: Exception) {
				e.printStackTrace()
				emit(Resource.Error(message = "Error Loading Movies"))
				return@flow
			}
			val movieEntities = movieListFromApi.results.map { entity ->
				entity.toMovieEntity(category)
			}
			movieDao.upsertMovieList(movieEntities)
			emit(Resource.Success(data = movieEntities.map {
				it.toMovie(category)
			}))
			emit(Resource.Loading(false))
		}
	}

	override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
		TODO("Not yet implemented")
	}

}