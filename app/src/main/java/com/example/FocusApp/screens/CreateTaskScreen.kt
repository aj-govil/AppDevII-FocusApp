package com.example.FocusApp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import com.example.FocusApp.data.Task
import com.example.FocusApp.viewmodels.TaskListViewModel
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.example.FocusApp.viewmodels.AccountInformationViewModel


/**
 * Contains a favorite song input form along with a list of songs the user has entered.
 * To be refactored into a screen that can hold and display all created tasks
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(
    taskListViewModel: TaskListViewModel,
    navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .semantics(mergeDescendants = true) {
                contentDescription = "Create Task Screen"
            }
    ) {
        Row (
            modifier = Modifier
                .semantics(mergeDescendants = true) {
                    contentDescription = "Create Task Header"
                }
        ) {
            Text(
                text = "CREATE TASK",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.1.em,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = FontFamily.Serif
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

        }

        // Text field for task title
        TextField(
            value = taskListViewModel.title.value,
            onValueChange = { taskListViewModel.title.value = it },
            placeholder = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .padding(8.dp)
                .semantics(mergeDescendants = true) {
                    contentDescription = "Task Title Input"
                },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        // Text field for task description
        TextField(
            value = taskListViewModel.description.value,
            onValueChange = { taskListViewModel.description.value = it },
            placeholder = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(5.dp)
                .padding(top = 1.dp, bottom = 2.dp)
                .padding(8.dp)
                .semantics(mergeDescendants = true) {
                    contentDescription = "Task Description Input"
                },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        // Text Field for time: Will be changed to using a TimePickerDialog in the future
        TextField(
            value = taskListViewModel.dueTime.value,
            onValueChange = { taskListViewModel.dueTime.value = it },
            placeholder = { Text("Time (hh:mm)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .padding(8.dp)
                .semantics(mergeDescendants = true) {
                    contentDescription = "Due Time Input"
                },
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
                val newTitle = taskListViewModel.title.value.text
                val newDescription = taskListViewModel.description.value.text
                val newTime = taskListViewModel.dueTime.value.text
                if (newTitle.isNotBlank() && newDescription.isNotBlank() && isValidTime(newTime)) {
                    taskListViewModel.taskList.add(Task(newTitle, newDescription, newTime, false))
                    taskListViewModel.title.value = TextFieldValue("")
                    taskListViewModel.description.value = TextFieldValue("")
                    taskListViewModel.dueTime.value = TextFieldValue("")
                }
            },
            enabled = taskListViewModel.title.value.text.isNotBlank() && taskListViewModel.description.value.text.isNotBlank() && taskListViewModel.dueTime.value.text.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(20.dp))
        ) {
            Text("Add Task", color = MaterialTheme.colorScheme.surface)
        }

        // Spacer to add some space between buttons
        Spacer(modifier = Modifier.height(2.dp))

        // Button to navigate to ViewTasks Screen
        Button(
            onClick = {
              navController.navigate("My Tasks")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(8.dp)
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(20.dp)
                )
                .semantics(mergeDescendants = true) {
                    contentDescription = "View Tasks Button"
                }
        ) {
            Text("View Tasks", color = MaterialTheme.colorScheme.surface)
        }




        Spacer(modifier = Modifier.height(5.dp))
    }
}



fun isValidTime(time: String): Boolean {
    val regex = Regex("^([01]?[0-9]|2[0-3]):[0-5][0-9]\$")
    return regex.matches(time)
}








