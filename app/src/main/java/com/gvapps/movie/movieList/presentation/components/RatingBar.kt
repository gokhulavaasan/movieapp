package com.gvapps.movie.movieList.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.StarHalf
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun RatingBar(
	starModifier: Modifier = Modifier,
	stars: Int = 5,
	rating: Double = 0.0,
	color: Color = Color.Yellow
) {
	val filledStars = kotlin.math.floor(rating).toInt()
	val unfilledStars = stars - (kotlin.math.ceil(rating).toInt())
	val halfStar = !(rating.rem(1).equals(0.0))
	Row() {
		repeat(filledStars) {
			Icon(
				imageVector = Icons.Rounded.Star,
				contentDescription = null,
				tint = color
			)
		}
		if (halfStar) {
			Icon(
				imageVector = Icons.AutoMirrored.Rounded.StarHalf,
				contentDescription = null,
				tint = color
			)
		}
		repeat(unfilledStars) {
			Icon(
				imageVector = Icons.Rounded.StarOutline,
				contentDescription = null,
				tint = color
			)
		}
	}
}