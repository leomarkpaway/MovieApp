package com.leomarkpaway.jetmoviedb.view.home.components.pager

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.leomarkpaway.jetmoviedb.model.source.remote.entity.Genre
import com.leomarkpaway.jetmoviedb.model.source.remote.entity.Movie
import com.leomarkpaway.jetmoviedb.ui.theme.white
import com.leomarkpaway.jetmoviedb.view.home.components.tags.InterestTag
import kotlin.math.abs
import kotlin.math.min

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviePagerItem(
    movie: Movie,
    genres: List<Genre>,
    isSelected: Boolean,
    offset: Float,
    addToWatchList: () -> Unit,
    openMovieDetail: () -> Unit
) {
    val animateHeight = getOffsetBasedValue(
        selectedValue = 645,
        nonSelectedValue = 360,
        isSelected = isSelected,
        offset = offset
    ).dp
    val animateWidth = getOffsetBasedValue(
        selectedValue = 340,
        nonSelectedValue = 320,
        isSelected = isSelected,
        offset = offset
    ).dp
    val animateElevation = getOffsetBasedValue(
        selectedValue = 12,
        nonSelectedValue = 2,
        isSelected = isSelected,
        offset = offset
    ).dp

    val posterFullPath = "https://image.tmdb.org/t/p/w500/${movie.poster_path}"

    val movieGenres = movie.genre_ids?.let { movieGenreIds ->
        genres.filter { movieGenreIds.contains(it.id) }.take(3)
    }

    Card(
        elevation = animateDpAsState(animateElevation).value,
        modifier = Modifier
            .width(animateWidth)
            .height(animateHeight)
            .padding(24.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = MaterialTheme.colors.onBackground,
        contentColor = MaterialTheme.colors.background,
        onClick = { openMovieDetail.invoke() },
    ) {
        Column {
            Image(
                painter = rememberImagePainter(data = posterFullPath),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(360.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = movie.title,
                    modifier = Modifier.padding(8.dp),
                    style = typography.h6
                )
            }
            Row {
                movieGenres?.forEach {
                    InterestTag(text = it.name)
                }
            }
            Text(
                text = "Release: ${movie.release_date}",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = typography.h6.copy(fontSize = 12.sp)
            )
            Text(
                text = "PG13  •  ${movie.vote_average}/10",
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = typography.h6.copy(fontSize = 12.sp, fontWeight = FontWeight.Medium)
            )
            Text(
                text = movie.overview,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(8.dp)
                    .weight(1f),
                style = typography.subtitle2
            )
            Button(onClick = { addToWatchList.invoke() }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Add to Watchlist", color = white, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

private fun getOffsetBasedValue(
    selectedValue: Int,
    nonSelectedValue: Int,
    isSelected: Boolean,
    offset: Float,
): Float {
    val actualOffset = if (isSelected) 1 - abs(offset) else abs(offset)
    val delta = abs(selectedValue - nonSelectedValue)
    val offsetBasedDelta = delta * actualOffset

    return min(selectedValue, nonSelectedValue) + offsetBasedDelta
}