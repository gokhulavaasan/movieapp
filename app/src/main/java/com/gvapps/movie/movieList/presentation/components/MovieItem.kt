package com.gvapps.movie.movieList.presentation.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.gvapps.movie.movieList.data.remote.MovieApi
import com.gvapps.movie.movieList.domain.model.Movie

@Composable
fun MovieItem(
	navHostController: NavController,
	movie: Movie
) {
	Column(
		modifier = Modifier
			.wrapContentHeight()
			.width(50.dp)
	) {
		AsyncImage(
			model = ImageRequest.Builder(LocalContext.current)
				.data(MovieApi.IMAGE_BASE_URL + movie.backdrop_path)
				.size(Size.ORIGINAL)
				.build(),
			onSuccess = {
			}
		)
	}
}

@Composable
private fun MovieItemPreview() {
	val navControllerExample = rememberNavController()
	val movie = Movie(
		adult = false,
		backdrop_path = "/3V4kLQg0kSqPLctI5ziYWabAZYF.jpg",
		genre_ids = listOf(28, 878, 12, 53),
		id = 912649,
		category = "popular",
		original_language = "en",
		original_title = "Venom: The Last Dance",
		overview = "Eddie and Venom are on the run. Hunted by both of their worlds and with the net closing in, the duo are forced into a devastating decision that will bring the curtains down on Venom and Eddie's last dance.",
		popularity = 9486.301,
		poster_path = "/aosm8NMQ3UyoBVpSxyimorCQykC.jpg",
		release_date = "2024-10-22",
		title = "Venom: The Last Dance",
		video = false,
		vote_average = 6.771,
		vote_count = 1568
	)
	MovieItem(
		movie = movie,
		navHostController = navControllerExample
	)
}



