package com.gvapps.movie.movieList.presentation.mainactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.gvapps.movie.movieList.presentation.details.DetailScreen
import com.gvapps.movie.movieList.util.Screen
import com.gvapps.movie.ui.theme.MovieTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			MovieTheme {
				enableEdgeToEdge()
				Surface(
					modifier = Modifier
						.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {
					val navController = rememberNavController()
					NavHost(
						navController = navController,
						startDestination = Screen.Home.route
					) {
						composable(
							Screen.Home.route
						) {
							HomeScreen(navController)
						}
						composable(
							Screen.Detail.route + "/{movieId}",
							arguments = listOf(
								navArgument("movieId") { type = NavType.IntType }
							)
						) {
							DetailScreen()
						}
					}
				}
			}
		}
	}


	@Composable
	fun setBarsColor(color: Color) {
		val systemUiController = rememberSystemUiController()
		LaunchedEffect(key1 = color) {
			systemUiController.setSystemBarsColor(color)
		}
	}
}
