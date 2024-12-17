package com.gvapps.movie.movieList.util

sealed class Screen(val route: String) {
	object Home : Screen("main")
	object PopularMovieList : Screen("popular")
	object UpcomingMovieList : Screen("upcoming")
	object Detail : Screen("detail")
}