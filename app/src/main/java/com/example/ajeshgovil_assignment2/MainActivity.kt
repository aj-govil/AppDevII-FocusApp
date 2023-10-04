@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.ajeshgovil_assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.ajeshgovil_assignment2.ui.theme.AjeshGovil_Assignment2Theme

/**
 * Main Activity containing a favorite song list application.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(SongListViewModel::class.java)
        setContent {
            AjeshGovil_Assignment2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.inversePrimary
                ) {
                    SongListApp(viewModel)
                }
            }
        }
    }
}

/**
 * Contains a favorite song input form along with a list of songs the user has entered.
 */
@Composable
fun SongListApp(viewModel: SongListViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //Header Banner
        Row {
            Text(
                text = "MY FAVORITE SONGS",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Blue,
                modifier = Modifier.padding(8.dp)
            )
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = null,
                tint = Color.Blue,
                modifier = Modifier.size(50.dp)
            )
        }

        // Text fields for song title and author
        TextField(
            value = viewModel.title.value,
            onValueChange = { viewModel.title.value = it },
            placeholder = { Text("Song Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )

        TextField(
            value = viewModel.author.value,
            onValueChange = { viewModel.author.value = it },
            placeholder = { Text("Author") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )

        // Button to add a song
        Button(
            onClick = {
                val newTitle = viewModel.title.value.text
                val newAuthor = viewModel.author.value.text
                if (newTitle.isNotBlank() && newAuthor.isNotBlank()) {
                    viewModel.songList.add(newTitle to newAuthor)
                    viewModel.title.value = TextFieldValue("")
                    viewModel.author.value = TextFieldValue("")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Add Song")
        }

        Spacer(modifier = Modifier.height(5.dp))

        // Displays the song list from the ViewModel
        LazyColumn {
            items(viewModel.songList) { song ->
                SongItem(song)
            }
        }
    }
}

/**
 * A song that the user has entered. Part of a list of favorite songs.
 */
@Composable
fun SongItem(song: Pair<String, String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        //An icon to display next to the song name
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier.size(24.dp)
        )

        //The favorite song to display
        Text(
            text = "${song.first} by ${song.second}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}