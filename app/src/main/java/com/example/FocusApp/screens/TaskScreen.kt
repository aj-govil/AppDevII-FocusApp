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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.FocusApp.viewmodels.AccountInformationViewModel


/**
 * Contains a favorite song input form along with a list of songs the user has entered.
 * To be refactored into a screen that can hold and display all created tasks
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongListApp(
    taskListViewModel: TaskListViewModel,
    accountInformationViewModel: AccountInformationViewModel) {

    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .semantics(mergeDescendants = true, properties = {})
            .fillMaxSize()
            .padding(16.dp)
    ) {
        //Header Banner
        Row () {
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
                    .semantics {
                        contentDescription = "Create Task"
                        role = Role.Tab
                    }
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
                .semantics {
                contentDescription = "Task Title"
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
                .semantics {
                    contentDescription = "Task Description"
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
                .semantics {
                    contentDescription = "Due Time"
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
                .semantics {
                    contentDescription = "Add Task"
                    role = Role.Button
                },
        ) {
            Text("Add Task", color = MaterialTheme.colorScheme.surface)
        }




        Spacer(modifier = Modifier.height(5.dp))

        Box(
            modifier = Modifier.weight(1f)
        ) {
            LazyColumn {
                items(taskListViewModel.taskList) { task ->
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
                modifier = Modifier
                    .height(48.dp)
                    .width(100.dp),
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
                    .semantics {
                        role = Role.DropdownList
                    },
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
                            value = accountInformationViewModel.firstName.value,
                            onValueChange = { accountInformationViewModel.firstName.value = it },
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
                            value = accountInformationViewModel.lastName.value,
                            onValueChange = { accountInformationViewModel.lastName.value = it },
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
                            value = accountInformationViewModel.age.value,
                            onValueChange = { accountInformationViewModel.age.value = it },
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
                            .height(48.dp)
                            .padding(5.dp)
                            .semantics {
                                contentDescription = "Save and Close"
                                role = Role.Button
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
                    color = MaterialTheme.colorScheme.inverseSurface
                )
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    modifier = Modifier.padding(vertical = 4.dp)
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
                color = if (checkedState.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { checkedState.value = !checkedState.value }
            .semantics {
                contentDescription = if (checkedState.value) "Checked" else "Unchecked"
                role = Role.Checkbox
            },
    ) {
        if (checkedState.value) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .size(40.dp)
                    .padding(4.dp)
            )
        }
    }
}

fun isValidTime(time: String): Boolean {
    val regex = Regex("^([01]?[0-9]|2[0-3]):[0-5][0-9]\$")
    return regex.matches(time)
}








