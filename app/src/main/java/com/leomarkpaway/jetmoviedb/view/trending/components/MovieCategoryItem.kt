package com.leomarkpaway.jetmoviedb.view.trending.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.leomarkpaway.jetmoviedb.model.source.remote.entity.Movie


@Composable
fun MovieCategoryItem(movies: List<Movie>, title: String = "", onMovieSelected: (Movie) -> Unit) {
    if (title.isNotEmpty()) {
        Text(
            text = title,
            style = typography.h6,
            modifier = Modifier.padding(start = 16.dp, end = 8.dp, bottom = 8.dp, top = 24.dp)
        )
    }
    LazyRow {
        items(
            items = movies,
            itemContent = { movie: Movie ->
                Image(
                    painter = rememberImagePainter(
                        data = "https://image.tmdb.org/t/p/w500/${movie.poster_path}",
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .width(190.dp)
                        .height(300.dp)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(onClick = { onMovieSelected(movie) }),
                    contentScale = ContentScale.Crop
                )
            })
    }
}