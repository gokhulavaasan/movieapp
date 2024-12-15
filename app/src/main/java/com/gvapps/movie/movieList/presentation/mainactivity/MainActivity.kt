package com.gvapps.movie.movieList.presentation.mainactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.gvapps.movie.ui.theme.MovieTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			MovieTheme {
				setBarsColor(MaterialTheme.colorScheme.inverseSurface)
				Surface(
					modifier = Modifier
						.fillMaxSize(),
					color = MaterialTheme.colorScheme.background
				) {

				}
			}
		}
	}

	@Composable
	fun setBarsColor(color: Color) {
		val systemUiController = rememberSystemUiController()
		systemUiController.setSystemBarsColor(color = color)
	}
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
	Text(
		text = "Hello $name!",
		modifier = modifier
	)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
	MovieTheme {
		Greeting("Android")
	}
}