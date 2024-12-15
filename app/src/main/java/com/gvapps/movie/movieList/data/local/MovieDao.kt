package com.gvapps.movie.movieList.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDao {
	@Upsert
	suspend fun upsertMovieList(movieList: List<MovieEntity>)

	@Query("Select * FROM MovieEntity where id = :id ")
	suspend fun getMovieById(id: Int): MovieEntity

	@Query("Select * from MovieEntity where category= :category")
	suspend fun getMovieListByCategory(category: String): List<MovieEntity>
}