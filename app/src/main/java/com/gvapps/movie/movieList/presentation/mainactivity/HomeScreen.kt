package com.gvapps.movie.movieList.presentation.mainactivity

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Upcoming
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gvapps.movie.movieList.presentation.categoryScreen.PopularMovieScreen
import com.gvapps.movie.movieList.presentation.categoryScreen.UpcomingMovieScreen
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
		},
		modifier = Modifier
			.statusBarsPadding()
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
						movieState = mainListState,
						onEvent = mainViewModel::onEvent
					)
				}
				composable(Screen.UpcomingMovieList.route) {
					UpcomingMovieScreen(
						navController = navController,
						movieState = mainListState,
						onEvent = mainViewModel::onEvent
					)
				}
			}
		}
	}
}

@Composable
fun BottomNavigationBar(
	bottomNavController: NavHostController,
	onEvent: (MainUiEvents) -> Unit
) {
	val items = listOf(
		BottomItems(title = "Popular", icon = Icons.Filled.Movie),
		BottomItems(title = "Upcoming", icon = Icons.Filled.Upcoming)
	)

	val selected = rememberSaveable {
		mutableIntStateOf(0)
	}
	NavigationBar {
		Row(
			modifier = Modifier.background(MaterialTheme.colorScheme.background),
		) {
			items.forEachIndexed { index, bottomItem ->
				NavigationBarItem(
					selected = selected.intValue == index,
					onClick = {
						selected.intValue = index
						when (selected.intValue) {
							0 -> {
								onEvent(MainUiEvents.Navigate)
								bottomNavController.popBackStack()
								bottomNavController.navigate(Screen.PopularMovieList.route)
							}

							1 -> {
								onEvent(MainUiEvents.Navigate)
								bottomNavController.popBackStack()
								bottomNavController.navigate(Screen.UpcomingMovieList.route)
							}
						}

					},
					icon = {
						Icon(
							imageVector = bottomItem.icon,
							contentDescription = bottomItem.title,
							tint = MaterialTheme.colorScheme.onBackground
						)
					},
					label = {
						Text(
							text = bottomItem.title
						)
					}
				)
			}
		}
	}

}

data class BottomItems(
	val title: String,
	val icon: ImageVector
)