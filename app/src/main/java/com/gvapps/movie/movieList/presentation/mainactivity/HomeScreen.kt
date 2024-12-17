package com.gvapps.movie.movieList.presentation.mainactivity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gvapps.movie.movieList.presentation.categoryScreen.PopularMovieScreen
import com.gvapps.movie.movieList.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
	navController: NavController
) {
	val bottomNavController = rememberNavController()
	val mainViewModel = hiltViewModel<MainViewModel>()
	val mainListState = mainViewModel.movieListState.collectAsState().value
	Scaffold(
		bottomBar = {
			BottomNavigationBar(
				bottomNavController = bottomNavController,
				onEvent = mainViewModel::onEvent
			)
		},
		topBar = {
			TopAppBar(
				title = {
					Text(
						text = if (mainListState.isCurrentPopularScreen) "Upcoming Movies" else "Popular movies",
						fontSize = 20.sp
					)
				}
			)
		}
	) {
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(it)
		) {
			NavHost(
				navController = bottomNavController,
				startDestination = Screen.PopularMovieList.route
			) {
				composable(Screen.PopularMovieList.route) {
					PopularMovieScreen(
						navController = navController,
					)
				}
				composable(Screen.UpcomingMovieList.route) {

				}
			}
		}
	}

}

fun BottomNavigationBar(
	bottomNavController: NavHostController,
	onEvent: (MainUiEvents) -> Unit
) {

}