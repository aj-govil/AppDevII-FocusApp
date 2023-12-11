package com.example.FocusApp.db

import com.example.FocusApp.data.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

class taskRepositoryFirestore(val db: FirebaseFirestore): TaskRepository {
    override suspend fun addTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun getTasks(userId: String): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTask(name: String): Flow<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(task: Task) {
        TODO("Not yet implemented")
    }
}