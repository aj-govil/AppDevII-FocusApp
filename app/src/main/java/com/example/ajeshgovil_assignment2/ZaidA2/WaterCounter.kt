import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicstatecodelab.WellnessTaskItem


@Composable
fun WaterCounter(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        // Changes to count are now tracked by compose
        var count by rememberSaveable { mutableStateOf(0)}
        if (count > 0){
            var showTask by rememberSaveable { mutableStateOf(true)}
            if(showTask){
                WellnessTaskItem(taskName = "Have you taken your 15 minutes walk today?",
                    modifier)
            }
            // This text is present if the button has been clicked
            // at least once; absent otherwise
            Text("You've had ${count} glasses.")
        }

        Row(Modifier.padding(top = 8.dp)){
            Button(
                onClick = { count++},
                enabled = count < 10) {
                    Text("Add one") // test commit
            }
            Button(
                onClick = { count = 0 },
                Modifier.padding(start = 8.dp)) {
                    Text("Clear water count")
            }

        }

    }
}