package com.example.basicstatecodelab

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.log

private fun getWellnessTasks() = List(0) { i -> WellnessTask(i, "Task # $i") }
var id = 0
@Composable
fun WellnessTasksList(
    modifier: Modifier = Modifier,
    list: MutableList<WellnessTask> = remember { getWellnessTasks().toMutableStateList()}
) {

    Log.d("HELLO", list.size.toString())
//    var list2 = rememberSaveable() {
//            mutableStateListOf(list)
//    }
    StatefulCounter(addToList = { list.add(WellnessTask(id++,it))}) // Counter to keep track of text input

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        if(list.size > 0 && list.size < 2) WellnessTaskItem(taskName = list[0].label) // only display first task

    }
}

