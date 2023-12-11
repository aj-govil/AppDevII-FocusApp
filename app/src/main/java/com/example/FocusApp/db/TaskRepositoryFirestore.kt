package com.example.FocusApp.db

import android.content.ContentValues.TAG
import android.util.Log
import com.example.FocusApp.data.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

class TaskRepositoryFirestore(val db: FirebaseFirestore): TaskRepository {

    // get database
    val dbTasks = db.collection("Tasks")
    override suspend fun addTask(task: Task) {
        dbTasks.add(task)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    override suspend fun getTasks(userId: String): Flow<List<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(task: Task) {
        TODO("Not yet implemented")
    }
}