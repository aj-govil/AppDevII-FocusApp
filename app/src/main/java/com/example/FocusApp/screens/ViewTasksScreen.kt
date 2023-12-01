package com.example.FocusApp.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.FocusApp.data.Task
import com.example.FocusApp.navigateSingleTopTo
import com.example.FocusApp.viewmodels.AccountInformationViewModel
import com.example.FocusApp.viewmodels.TaskListViewModel


/**
 * Screen used to create a new task
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewTasksScreen(
    taskListViewModel: TaskListViewModel,
    accountInformationViewModel: AccountInformationViewModel,
    navController: NavController
) {

    var expanded by rememberSaveable { mutableStateOf(false) }

    Column (modifier = Modifier
        .fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.9f)
        ) {
            items(taskListViewModel.taskList) { task ->
                TaskItem(task)
            }

            item {
                Spacer(modifier = Modifier)

                FloatingActionButton(
                    onClick = {
                        navController.navigate("Add Task")
                    },
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
        ) {
            Button(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
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

                    Row {
                        Text(
                            text = "First\nName:",
                            modifier = Modifier
                                .fillMaxWidth(0.2f)
                                .padding(8.dp)
                        )

                        TextField(
                            value = accountInformationViewModel.firstName.value,
                            onValueChange = { accountInformationViewModel.firstName.value = it },
                            placeholder = { Text("Enter first name") },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(5.dp)
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
                            onValueChange = { accountInformationViewModel.lastName.value = it },
                            placeholder = { Text("Enter last name") },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(5.dp)
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
                            onValueChange = { accountInformationViewModel.age.value = it },
                            placeholder = { Text("Enter your age") },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .padding(5.dp)
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
                            onValueChange = { accountInformationViewModel.email.value = it },
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
 * A task that the user has entered.
 */
@Composable
fun TaskItem(task: Task) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Task details column
            Column(
                modifier = Modifier
                    .weight(1f) // Takes up most of the available space
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "Due: ${task.dueTime}",
                        style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
                        color = Color.Gray
                    )
                }
            }

            // TaskCheckBox outside the column, positioned to the right
            TaskCheckBox(isChecked = task.isComplete)
        }
    }
}

@Composable
fun TaskCheckBox(isChecked: Boolean) {
    val checkedState = rememberSaveable { mutableStateOf(isChecked) }

    Box(
        modifier = Modifier
            .size(30.dp)
            .background(
                color = if (checkedState.value) MaterialTheme.colorScheme.primary else Color.White,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { checkedState.value = !checkedState.value }
    ) {
        if (checkedState.value) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
            )
        }
    }
}

