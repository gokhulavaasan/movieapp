package com.gvapps.movie.movieList.presentation.mainactivity

sealed interface MainUiEvents {

	data class Paginate(val category: String) : MainUiEvents
	object Navigate: MainUiEvents

}