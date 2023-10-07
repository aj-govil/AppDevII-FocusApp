/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.woof

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ajeshgovil_assignment2.R
import com.example.woof.data.Episode
import com.example.woof.data.episodes
import com.example.woof.ui.theme.BreakingBadTheme


/**
 * This application was based on the code from the "Woof App" from the material theming with jetpack compose below: 
 *  https://developer.android.com/codelabs/basic-android-kotlin-compose-material-theming#3
 */


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BreakingBadTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    EpisodeApp()
                }
            }
        }
    }
}

/**
 * Composable that displays a searchbar and a list of episodes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeApp() {
    var searchText by rememberSaveable { mutableStateOf("") }
    var matchingEpisode by rememberSaveable { mutableStateOf<Episode?>(null) }

    Scaffold(
        topBar = {
            EpisodeSearch(episodes)
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {

            items(episodes) {
                EpisodeItem(
                    episode = it,
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }
    }
}


/**
 * Composable that displays a list item containing an episode icon and episode information.
 *
 * @param episode contains the data that populates the list item
 * @param modifier modifiers to set to this composable
 */
@Composable
fun EpisodeItem(
    episode: Episode,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            EpisodeIcon(episode.imageIcon)
            EpisodeInformation(episode.title, episode.position, episode.description, episode.releaseDate, episode.rank)
        }
    }
}


/**
 * Composable that displays a list item with a green border, containing an episode icon and episode information. (Used for found serach results)
 *
 * @param episode contains the data that populates the list item
 * @param modifier modifiers to set to this composable
 */
@Composable
fun HighlightedEpisodeItem(
    episode: Episode,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .border(4.dp, Color(0xFF006400))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            EpisodeIcon(episode.imageIcon)
            EpisodeInformation(episode.title, episode.position, episode.description, episode.releaseDate, episode.rank)
        }
    }
}



/**
 * Composable that displays a photo of a episode.
 *
 * @param episodeIcon is the resource ID for the image of the episode
 * @param modifier modifiers to set to this composable
 */
@Composable
fun EpisodeIcon(
    @DrawableRes episodeIcon: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(dimensionResource(R.dimen.image_size))
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop,
        painter = painterResource(episodeIcon),

        contentDescription = null
    )
}

/**
 * Composable that displays an episodes title, position, releaseDate, description and rank.
 *
 * @param title is the string that represents the episode title
 * @param position is the string that represents the season and episode number
 * @param releaseDate is the string that represents the date the episode was released
 * @param description is the string that represents a brief description of the episode
 * @param rank is the float number that represents the episode's score on IMDB
 * @param modifier modifiers to set to this composable
 */
@Composable
fun EpisodeInformation(
    title: String,
    position: String,
    description: String,
    releaseDate: String,
    rank: Float,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "$title - $position",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
        )
        Text(
            text = " $releaseDate",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "$description",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "$rank",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(255, 165, 0)
        )
    }
}


/**
 * Composable that checks if the users search matches an exsisting episode title and highlights that episode.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeSearch(
    episodes: List<Episode>,
    modifier: Modifier = Modifier
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    var matchingEpisode by rememberSaveable { mutableStateOf<Episode?>(null) }


    Column(modifier = modifier) {
        // Search bar
        TextField(
            value = searchText,
            onValueChange = { newText ->
                searchText = newText
                // Search for the matching episode by title
                matchingEpisode = episodes.find { it.title.equals(newText, ignoreCase = true) }
            },
            label = { Text("Search by Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        matchingEpisode?.let { episode ->
            HighlightedEpisodeItem(episode = episode,  modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)))
        }


    }
}


