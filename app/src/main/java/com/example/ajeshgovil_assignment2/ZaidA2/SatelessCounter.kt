package com.example.basicstatecodelab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun StatelessCounter(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {

    Column(modifier = modifier.padding(16.dp)) {
//        if (count > 0) {
//            Text("You've had $count glasses.")
//        }
        if(count > 0){
            Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 2) {
                if(count >= 2)
                Text(text = "Go Outside")
                else
                    Text("Click me once task is done")

            }
//            if(count > 2){
//            }
        }
        else {
            Button(onClick = onIncrement, Modifier.padding(top = 8.dp), enabled = count < 10) {
                Text("Add One Task for Today")

            }
        }

    }
}
