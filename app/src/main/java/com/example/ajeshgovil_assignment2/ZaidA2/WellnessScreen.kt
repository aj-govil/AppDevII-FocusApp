package com.example.basicstatecodelab

import WaterCounter
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier

private fun getWellnessTasks() = List(2) { i -> WellnessTask(i, "Task # $i") }
//var id = 0

@Composable
fun WellnessScreen(modifier: Modifier = Modifier) {

   // val listOfTasks = remember { getWellnessTasks().toMutableStateList()}
    //WaterCounter(modifier)
    //StatefulCounter()
    Column(modifier){
       // StatefulCounter()

        // This app does not work as intended, i didnt finish the ViewModel codelab to make it work better
        // So current functionality is :
        WellnessTasksList()
    }
}