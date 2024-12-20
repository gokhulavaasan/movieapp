package com.gvapps.movie.movieList.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.gvapps.movie.movieList.data.remote.MovieApi
import com.gvapps.movie.movieList.presentation.components.RatingBar
import java.util.Locale

@Composable
fun DetailScreen() {
	val detailViewModel = hiltViewModel<DetailViewModel>()
	val detailState = detailViewModel.detailState.collectAsState().value
	val backdropImageState = rememberAsyncImagePainter(
		model = ImageRequest.Builder(LocalContext.current)
			.data(MovieApi.IMAGE_BASE_URL + detailState.movie?.backdrop_path)
			.size(Size.ORIGINAL)
			.build()
	).state
	val posterImageState = rememberAsyncImagePainter(
		model = ImageRequest.Builder(LocalContext.current)
			.data(MovieApi.IMAGE_BASE_URL + detailState.movie?.poster_path)
			.size(Size.ORIGINAL)
			.build()
	).state
	val movieDetail = detailState.movie
	Column(
		modifier = Modifier
			.fillMaxSize()
			.statusBarsPadding()
			.verticalScroll(rememberScrollState())
	) {
		if (backdropImageState is AsyncImagePainter.State.Error) {
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.height(250.dp),
				contentAlignment = Alignment.Center
			) {
				Icon(
					imageVector = Icons.Filled.ImageNotSupported,
					contentDescription = movieDetail?.title,
					modifier = Modifier.size(70.dp)
				)
			}
		}
		if (backdropImageState is AsyncImagePainter.State.Success) {
			Image(
				painter = backdropImageState.painter,
				contentDescription = movieDetail?.title,
				modifier = Modifier
					.fillMaxWidth()
					.height(250.dp),
				contentScale = ContentScale.Crop
			)
		}
		Spacer(modifier = Modifier.height(20.dp))
		Row(
			modifier = Modifier.fillMaxWidth().padding(8.dp)
		) {
			Box(
				modifier = Modifier
					.width(160.dp)
					.height(240.dp)
			) {
				if (posterImageState is AsyncImagePainter.State.Error) {
					Box(
						modifier = Modifier
							.fillMaxSize()
							.statusBarsPadding()
							.clip(RoundedCornerShape(20.dp)),
						contentAlignment = Alignment.Center
					) {
						Icon(
							imageVector = Icons.Filled.ImageNotSupported,
							contentDescription = movieDetail?.title,
							modifier = Modifier.size(70.dp)
						)
					}
				}
				if (posterImageState is AsyncImagePainter.State.Success) {
					Image(
						painter = posterImageState.painter,
						contentDescription = movieDetail?.title,
						modifier = Modifier
							.fillMaxSize()
							.clip(RoundedCornerShape(20.dp)),
						contentScale = ContentScale.Crop
					)
				}
			}
			Spacer(modifier = Modifier.width(12.dp))
			detailState.movie?.let { movie ->
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.padding(top = 8.dp)
				) {
					Text(
						text = movie.title,
						fontSize = 15.sp,
						fontWeight = FontWeight.SemiBold
					)
					Spacer(modifier = Modifier.height(12.dp))
					Row(
						modifier = Modifier
					) {
						RatingBar(
							starModifier = Modifier.size(13.dp),
							rating = movie.vote_average / 2
						)
						Text(
							modifier = Modifier.padding(start = 2.dp)
								.align(Alignment.CenterVertically),
							text = String.format(Locale.ROOT, "%.1f", movie.vote_average),
							fontSize = 13.sp
						)
					}
					Text(
						text = "Language: ${movie.original_language}",
						fontSize = 15.sp
					)
					Text(
						text = "Release Date: ${movie.release_date}",
						fontSize = 15.sp
					)
					Text(
						text = "Votes: ${movie.vote_count}",
						fontSize = 15.sp
					)
				}
			}
		}
		Spacer(modifier = Modifier.height(32.dp))
		Text(
			text = "Overview",
			fontSize = 20.sp,
			fontWeight = FontWeight.Bold,
			modifier = Modifier
				.padding(start = 16.dp)
		)
		Spacer(modifier = Modifier.height(8.dp))
		detailState.movie?.let {
			Text(
				text = it.overview,
				fontSize = 16.sp,
				modifier = Modifier
					.padding(start = 16.dp)
			)
		}
		Spacer(modifier = Modifier.height(50.dp))
	}
}