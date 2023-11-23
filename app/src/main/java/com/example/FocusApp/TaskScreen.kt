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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.FocusApp.ui.theme.Task
import com.example.FocusApp.ui.theme.TaskListViewModel
import java.util.Date
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import java.sql.Time
import java.util.Calendar


/**
 * Contains a favorite song input form along with a list of songs the user has entered.
 * To be refactored into a screen that can hold and display all created tasks
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongListApp(viewModel: TaskListViewModel) {

    var expanded by rememberSaveable { mutableStateOf(false) }
    var userInput by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //Header Banner
        Row {
            Text(
                text = "MY TASKS",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.1.em,
                    color = Color.Blue,
                    fontFamily = FontFamily.Serif
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.Gray.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

        }

        // Text field for task title
        TextField(
            value = viewModel.title.value,
            onValueChange = { viewModel.title.value = it },
            placeholder = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .padding(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        // Text field for task description
        TextField(
            value = viewModel.description.value,
            onValueChange = { viewModel.description.value = it },
            placeholder = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(5.dp)
                .padding(top = 1.dp, bottom = 8.dp)
                .padding(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        TextField(
            value = userInput,
            onValueChange = { userInput = it },
            placeholder = { Text("Enter Time (hh:mm)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .padding(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )


        // Button to add a task
        Button(
            onClick = {
                val newTitle = viewModel.title.value.text
                val newDescription = viewModel.description.value.text
                val newTime = viewModel.dueTime.value
                if (newTitle.isNotBlank() && newDescription.isNotBlank()) {
                    viewModel.taskList.add(Task(newTitle, newDescription, newTime, false))
                    viewModel.title.value = TextFieldValue("")
                    viewModel.description.value = TextFieldValue("")
                    viewModel.dueTime.value = Date()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(5.dp))

        Box(
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn {
                items(viewModel.taskList) { task ->
                    TaskItem(task)
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
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
                    Row {
                        Text(
                            text = "Account Information",
                            modifier = Modifier
                                .padding(8.dp),
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.inverseSurface,
                            modifier = Modifier.size(50.dp)
                        )
                    }

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
                            .padding(25.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.inverseSurface)
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
fun TaskItem(task: Task) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = task.title,
            style = MaterialTheme.typography.titleSmall,
            color = Color.Black
        )
        Text(
            text = task.description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        Text(
            text = "Due: ${task.dueTime}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

fun parseTimeString(timeString: String): Time {
    val parts = timeString.split(":")
    if (parts.size == 2) {
        try {
            val hour = parts[0].toInt()
            val minute = parts[1].toInt()

            // Ensure the hour and minute values are within a valid range
            if (hour in 0..23 && minute in 0..59) {
                return Time(hour, minute, 0)
            }
        } catch (e: NumberFormatException) {
            // Ignore parsing errors, return default value
        }
    }

    // Default to current time if the input is not in the expected format
    val currentTime = Calendar.getInstance()
    return Time(currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), 0)
}




