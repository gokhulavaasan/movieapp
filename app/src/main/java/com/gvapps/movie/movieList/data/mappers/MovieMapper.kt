package com.gvapps.movie.movieList.data.mappers

import com.gvapps.movie.movieList.data.local.MovieEntity
import com.gvapps.movie.movieList.data.remote.dto.MovieDto
import com.gvapps.movie.movieList.domain.model.Movie
import kotlin.text.split

fun MovieEntity.toMovie(
	category: String
): Movie {
	return Movie(
		adult = adult,
		backdrop_path = backdrop_path,
		genre_ids = try {
			genre_ids.split(",").map { it.toInt() }
		} catch (e: Exception) {
			listOf(-1, -2)
		},
		id = id,
		original_language = original_language,
		original_title = original_title,
		overview = overview,
		popularity = popularity,
		poster_path = poster_path,
		release_date = release_date,
		title = title,
		video = video,
		vote_average = vote_average,
		vote_count = vote_count,
		category = category
	)
}

fun MovieDto.toMovieEntity(
	category: String
): MovieEntity {
	return MovieEntity(
		adult = adult == true,
		backdrop_path = backdrop_path ?: "",
		genre_ids = try {
			genre_ids?.joinToString(",") ?: "-1-2"
		} catch (e: Exception) {
			"-1,-2"
		},
		id = id ?: -1,
		original_language = original_language ?: "",
		original_title = original_title ?: "",
		overview = overview ?: "",
		popularity = popularity ?: 0.0,
		poster_path = poster_path ?: "",
		release_date = release_date ?: "",
		title = title ?: "",
		video = video ?: false,
		vote_average = vote_average ?: 0.0,
		vote_count = vote_count ?: 0,
		category = category
	)
}
