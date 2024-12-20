package com.gvapps.movie.movieList.presentation.categoryScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gvapps.movie.movieList.Category
import com.gvapps.movie.movieList.presentation.components.MovieItem
import com.gvapps.movie.movieList.presentation.mainactivity.MainState
import com.gvapps.movie.movieList.presentation.mainactivity.MainUiEvents

@Composable
fun UpcomingMovieScreen(
	navController: NavController,
	movieState: MainState,
	onEvent: (MainUiEvents) -> Unit
) {
	if (movieState.upcomingMovieList.isEmpty()) {
		Box(
			modifier = Modifier.fillMaxSize(),
			contentAlignment = Alignment.Center
		) {
			CircularProgressIndicator()
		}
	} else {
		LazyVerticalGrid(
			modifier = Modifier
				.fillMaxSize(),
			columns = GridCells.Fixed(2),
			contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
		) {
			items(movieState.upcomingMovieList.size) {
				MovieItem(
					navHostController = navController,
					movie = movieState.upcomingMovieList[it]
				)
				Spacer(modifier = Modifier.height(16.dp))
				if (it >= (movieState.upcomingMovieList.size - 1) && !movieState.isLoading) {
					onEvent(MainUiEvents.Paginate(Category.UPCOMING))
				}
			}
		}
	}
}