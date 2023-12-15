package com.example.FocusApp.db

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.FocusApp.data.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow

// Class setup inspired by Eikes Team Project and Talibs Kotlinwithcompose FirestoreCRUD Branch
// Note: Currently not being used because setup requires a bit more work than anticipated
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
//        val subscription = dbTasks
//            .whereEqualTo("userId", userId)
//            .addSnapshotListener { snapshot, error ->
//                if (error != null) {
//                    println("Listen failed: $error")
//                    return@addSnapshotListener
//                }
//                // if tasks exist
//                if (snapshot != null) {
//                    var tasks: MutableList<Task> = mutableListOf()
//                    for (document in snapshot.documents) {
//
//                        val task = convertSnapshotToTask(document)
//                        tasks.add(task)
//                    }
//                    if(tasks != null){
//                        trySend(tasks)
//                    }
//                }
//            }
        TODO("Not yet implemented")
    }

    override suspend fun delete(task: Task) {
        TODO("Not yet implemented")
    }
}

fun convertSnapshotToTask(document: DocumentSnapshot): Task{

    val taskList = mutableListOf<Task>()

    val task = Task(
        userId = document.getString("userId")?:"",
        title = document.getString("title") ?: "",
        description = document.getString("description") ?: "",
        dueTime = document.getString("dueTime") ?: "",
        isComplete = document.getBoolean("isComplete") ?: false,
        delete = false
    )

    return task
}