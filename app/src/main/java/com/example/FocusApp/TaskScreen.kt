package com.example.FocusApp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.drawerlayout.widget.DrawerLayout
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

/**
 * Contains a favorite song input form along with a list of songs the user has entered.
 * To be refactored into a screen that can hold and display all created tasks
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongListApp(viewModel: SongListViewModel) {

    var expanded by rememberSaveable { mutableStateOf(false) }

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
        /*LazyColumn {
            items(viewModel.songList) { song ->
                SongItem(song)
            }
        }*/
        Box(
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn {
                items(viewModel.songList) { song ->
                    SongItem(song)
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                /*.padding(8.dp)*/
        ) {
            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth(0.2f),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.inversePrimary)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                //Text("Account Information")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.inverseOnSurface)
                        .padding(30.dp)
                ) {
                    Text(text = "Account Information",
                        modifier = Modifier
                            .padding(8.dp),
                        style = MaterialTheme.typography.headlineMedium,)

                    Row{
                        Text(
                            text = "First\nName:",
                            modifier = Modifier
                                .fillMaxWidth(0.2f)
                                .padding(8.dp)
                        )

                        TextField(
                            value = viewModel.firstName.value,
                            onValueChange = { viewModel.firstName.value = it },
                            placeholder = { Text("Enter first name") },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(5.dp)
                        )
                    }

                    Row{
                        Text(
                            text = "Last\nName:",
                            modifier = Modifier
                                .fillMaxWidth(0.2f)
                                .padding(8.dp)
                        )

                        TextField(
                            value = viewModel.lastName.value,
                            onValueChange = { viewModel.lastName.value = it },
                            placeholder = { Text("Enter last name") },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(5.dp)
                        )
                    }

                    Row{
                        Text(
                            text = "Age:",
                            modifier = Modifier
                                .fillMaxWidth(0.2f)
                                .padding(8.dp)
                        )

                        TextField(
                            value = viewModel.age.value,
                            onValueChange = { viewModel.age.value = it },
                            placeholder = { Text("Enter your age") },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(5.dp)
                        )
                    }

                    Row{
                        Text(
                            text = "Email:",
                            modifier = Modifier
                                .fillMaxWidth(0.2f)
                                .padding(8.dp)
                        )

                        TextField(
                            value = viewModel.email.value,
                            onValueChange = { viewModel.email.value = it },
                            placeholder = { Text("Enter email address") },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(5.dp)
                        )
                    }

                    Button(
                        onClick = { expanded = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(25.dp)
                    ) {
                        Text("Save and Close")
                    }
                }
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

