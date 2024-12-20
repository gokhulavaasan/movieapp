package com.gvapps.movie.movieList.presentation.components


import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.gvapps.movie.movieList.data.remote.MovieApi
import com.gvapps.movie.movieList.domain.model.Movie
import com.gvapps.movie.movieList.util.Screen
import com.gvapps.movie.movieList.util.getAverageColor
import java.util.Locale

@Composable
fun MovieItem(
	navHostController: NavController,
	movie: Movie
) {
	val imageState = rememberAsyncImagePainter(
		model = ImageRequest.Builder(LocalContext.current)
			.data(MovieApi.IMAGE_BASE_URL + movie.backdrop_path)
			.size(Size.ORIGINAL)
			.build()
	).state
	val defaultColor = MaterialTheme.colorScheme.secondaryContainer
	var dominantColor by remember {
		mutableStateOf(defaultColor)
	}

	Column(
		modifier = Modifier
			.wrapContentHeight()
			.width(200.dp)
			.padding(8.dp)
			.clip(RoundedCornerShape(28.dp))
			.background(
				Brush.verticalGradient(
					listOf(
						MaterialTheme.colorScheme.secondaryContainer,
						dominantColor
					)
				)

			)
			.clickable { navHostController.navigate(Screen.Detail.route + "/${movie.id}") }
	) {
		if (imageState is AsyncImagePainter.State.Error) {
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.padding(6.dp)
					.height(250.dp)
					.clip(RoundedCornerShape(22.dp))
					.background(MaterialTheme.colorScheme.primaryContainer),
				contentAlignment = Alignment.Center
			) {
				Icon(
					imageVector = Icons.Default.ImageNotSupported,
					contentDescription = movie.title,
					modifier = Modifier
						.size(70.dp)
				)
			}
		} else if (imageState is AsyncImagePainter.State.Success) {
			dominantColor = getAverageColor(
				imageBitmap = imageState.result.drawable.toBitmap().asImageBitmap()
			)
			Image(
				painter = imageState.painter,
				contentDescription = movie.title,
				modifier = Modifier
					.fillMaxWidth()
					.padding(6.dp)
					.height(250.dp)
					.clip(RoundedCornerShape(22.dp)),
				contentScale = ContentScale.Crop
			)
		}
		Spacer(modifier = Modifier.height(2.dp))
		Text(
			text = movie.title,
			modifier = Modifier
				.padding(start = 16.dp, end = 6.dp),
			color = Color.White,
			fontSize = 17.sp,
			maxLines = 1,
		)
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(start = 16.dp, bottom = 10.dp, top = 4.dp)
		) {
			RatingBar(starModifier = Modifier.size(13.dp), rating = movie.vote_average / 2)
			Text(
				modifier = Modifier.padding(start = 2.dp).align(Alignment.CenterVertically),
				text = String.format(Locale.ROOT, "%.1f", movie.vote_average),
				color = Color.White,
				fontSize = 13.sp
			)
		}
	}
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
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



