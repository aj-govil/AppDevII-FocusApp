package com.example.FocusApp.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.FocusApp.Generators
import com.example.FocusApp.data.Task
import com.example.FocusApp.viewmodels.AccountInformationViewModel
import com.example.FocusApp.viewmodels.ProfileViewModel
import com.example.FocusApp.viewmodels.TaskListViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


/**
 * Screen used to create a new task
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewTasksScreen(
    taskListViewModel: TaskListViewModel,
    accountInformationViewModel: AccountInformationViewModel,
    profileViewModel: ProfileViewModel,
    navController: NavController,
    db: FirebaseFirestore,
    auth: FirebaseAuth
) {

    var expanded by rememberSaveable { mutableStateOf(false) }
    var filterType by rememberSaveable { mutableStateOf(FilterType.ALL) }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    // state of profile view model
    val myUiState by profileViewModel.uiState.collectAsState()

    // The fllowing logic should be added in a viewmodel for firestore
    // Get current User UID
    val currentUser = auth.currentUser
    val userId = currentUser?.uid

    db.collection("Tasks").whereEqualTo("userId", userId)
        .get()
        .addOnSuccessListener { result ->
            // Every fetch, clear viewmodel
            taskListViewModel.taskList.clear()

            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
                // Manually fetch task data
                val task = Task(
                    userId = userId,
                    title = document.getString("title") ?: "",
                    description = document.getString("description") ?: "",
                    dueTime = document.getString("dueTime") ?: "",
                    isComplete = document.getBoolean("isComplete") ?: false
                )
                // add data to viewmodel
                taskListViewModel.taskList.add(task)
            }
        }
        .addOnFailureListener { exception ->
            Log.w(TAG, "Error getting documents.", exception)
        }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .semantics(mergeDescendants = true,)
            {
                contentDescription = "Main Screen"
            })
    {
        // Search bar row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .semantics(mergeDescendants = true) {
                    contentDescription = "Search Bar Row"
                }
        ) {

            BasicTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                textStyle = LocalTextStyle.current.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Left,
                    fontFamily = FontFamily.Serif
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp, end = 4.dp)
                    .height(48.dp)
                    .background(MaterialTheme.colorScheme.inversePrimary, shape = RoundedCornerShape(4.dp)),
                singleLine = true,
                cursorBrush = SolidColor(MaterialTheme.colorScheme.inversePrimary),
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.CenterStart,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (searchQuery.isEmpty()) {
                            // Show the search icon as a placeholder if the text is empty
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }


        // Filter Buttons Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .semantics(mergeDescendants = true) {
                    contentDescription = "Filter Buttons"
                }
        ) {
            FilterButton("All") { filterType = FilterType.ALL }
            Spacer(modifier = Modifier.width(8.dp))
            FilterButton("Complete ") { filterType = FilterType.COMPLETE }
            Spacer(modifier = Modifier.width(8.dp))
            FilterButton("Incomplete ") { filterType = FilterType.INCOMPLETE }

        }


        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.9f)
                .semantics(mergeDescendants = true) {
                    contentDescription = "Task List"
                }
        ) {
            val filteredTasks = getFilteredTasks(taskListViewModel.taskList, filterType)
                .sortedByDescending { it.title.contains(searchQuery, ignoreCase = true) }

            items(filteredTasks) { task ->
                TaskItem(task)
            }

            item {
                Spacer(modifier = Modifier)

                FloatingActionButton(
                    onClick = {
                        navController.navigate(Generators.route)
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .size(50.dp)
                        .semantics(mergeDescendants = true) {
                            contentDescription = "Add Task Button"
                        }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
                .semantics(mergeDescendants = true) {
                    contentDescription = "Settings Section"
                }
        ) {
            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .semantics(mergeDescendants = true) {
                        contentDescription = "Settings Button"
                    },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.inversePrimary)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.inverseSurface)
                    .fillMaxWidth()
                    .semantics(mergeDescendants = true) {
                        contentDescription = "Settings Menu"
                    }
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
                                .padding(8.dp)
                                .semantics(mergeDescendants = true) {
                                    contentDescription = "Account Information"
                                },
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.inverseSurface,
                            modifier = Modifier
                                .size(50.dp)
                                .semantics(mergeDescendants = true) {
                                    contentDescription = "Account Information Icon"
                                },
                        )
                    }

                    Row {
                        Text(
                            text = "First\nName:",
                            modifier = Modifier
                                .fillMaxWidth(0.2f)
                                .padding(8.dp)
                        )

                        TextField(
                            value = accountInformationViewModel.firstName.value,
                            onValueChange = {
                                            accountInformationViewModel.firstName.value = it
                                            myUiState.name = it.text // set name in UI state (for datastore)
                                            },
                            placeholder = { Text(myUiState.name) },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(5.dp)
                                .semantics(mergeDescendants = true) {
                                    contentDescription = "First Name Text Field"
                                },
                        )
                    }

                    Row {
                        Text(
                            text = "Last\nName:",
                            modifier = Modifier
                                .fillMaxWidth(0.2f)
                                .padding(8.dp)
                        )

                        TextField(
                            value = accountInformationViewModel.lastName.value,
                            onValueChange = {
                                                accountInformationViewModel.lastName.value = it
                                                myUiState.lastName = it.text // set lastName in UI State (for datastore)
                                            },
                            placeholder = { Text(myUiState.lastName) },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(5.dp)
                                .semantics(mergeDescendants = true) {
                                    contentDescription = "Last Name Text Field"
                                },
                        )
                    }

                    Row {
                        Text(
                            text = "Age:",
                            modifier = Modifier
                                .fillMaxWidth(0.2f)
                                .padding(8.dp)
                        )

                        TextField(
                            value = accountInformationViewModel.age.value,
                            onValueChange = {
                                                accountInformationViewModel.age.value = it
                                                myUiState.age = it.text.toInt() // set age in UiState (for datastore)
                                            },
                            placeholder = { Text(myUiState.age.toString()) },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(5.dp)
                                .semantics(mergeDescendants = true) {
                                    contentDescription = "Age Text Field"
                                },
                        )
                    }

                    Row {
                        Text(
                            text = "Email:",
                            modifier = Modifier
                                .fillMaxWidth(0.2f)
                                .padding(8.dp)
                        )

                        TextField(
                            value = accountInformationViewModel.email.value,
                            onValueChange = {
                                                accountInformationViewModel.email.value = it
                                                myUiState.email = it.text // set email in UI State (for datastore)
                                            },
                            placeholder = { Text(myUiState.email) },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(5.dp)
                                .semantics(mergeDescendants = true) {
                                    contentDescription = "Email Text Field"
                                },
                        )
                    }

                    Button(
                        onClick = {
                            expanded = false
                            // Set Profile info in viewmodel - in turn saves in datastore
                            profileViewModel.setProfileData(myUiState.name, myUiState.lastName, myUiState.email, myUiState.age)
                                  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(25.dp)
                            .height(48.dp)
                            .semantics(mergeDescendants = true) {
                                contentDescription = "Save and Close Button"
                            },
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
 * A task that the user has entered.
 */
@Composable
fun TaskItem(task: Task) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .semantics(mergeDescendants = true) {
                contentDescription = "A task item"
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    modifier = Modifier.padding(vertical = 4.dp),
                    fontFamily = FontFamily.Serif
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "Due: ${task.dueTime}",
                        style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
                        color = MaterialTheme.colorScheme.inverseSurface
                    )
                }
            }

            TaskCheckBox(task)
        }
    }
}

@Composable
fun TaskCheckBox(task: Task) {
    // Initialize checkedState based on the task's isComplete value
    val checkedState = rememberSaveable { mutableStateOf(task.isComplete) }

    Box(
        modifier = Modifier
            .size(48.dp)
            .background(
                color = if (checkedState.value && task.isComplete) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable {
                checkedState.value = !checkedState.value
                // Update the task's isComplete field
                task.isComplete = checkedState.value
            }
    ) {
        if (task.isComplete) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .padding(4.dp)
                    .size(48.dp)
            )
        }
    }
}



enum class FilterType {
    ALL,
    COMPLETE,
    INCOMPLETE
}

@Composable
fun FilterButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(48.dp)
            .semantics(mergeDescendants = true) {
                contentDescription = "$text Button"
            },
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.inversePrimary)
    ) {
        Text(text = text)
    }
}

fun getFilteredTasks(tasks: List<Task>, filterType: FilterType): List<Task> {
    return when (filterType) {
        FilterType.ALL -> tasks
        FilterType.COMPLETE -> tasks.filter { it.isComplete }
        FilterType.INCOMPLETE -> tasks.filterNot { it.isComplete }
    }
}



