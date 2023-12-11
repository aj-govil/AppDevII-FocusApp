package com.example.FocusApp.db

import com.example.FocusApp.data.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun addTask(task: Task)
    suspend fun getTasks(userId: String): Flow<List<Task>>

    // suspend fun getTasks(): Flow<List<Task>> // Do we need to get all tasks?
    suspend fun delete(task: Task)
}