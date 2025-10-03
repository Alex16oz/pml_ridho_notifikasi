package yuga.ridho.pml3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yuga.ridho.pml3.ui.theme.Pml_ridho_notifikasiTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Pml_ridho_notifikasiTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("incoming massages", color = Color.White) },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color.Blue
                            )
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MessageContent(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MessageContent(modifier: Modifier = Modifier) {
    var text1 by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }

    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            value = text1,
            onValueChange = { text1 = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("") },
            maxLines = 5
        )
        Text(
            text = "notification",
            modifier = Modifier.padding(top = 16.dp)
        )
        TextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            label = { Text("title") }
        )
        TextField(
            value = body,
            onValueChange = { body = it },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            label = { Text("Body") }
        )
        Text(
            text = "data",
            modifier = Modifier.padding(top = 16.dp)
        )
        TextField(
            value = text2,
            onValueChange = { text2 = it },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            label = { Text("") },
            maxLines = 5
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Pml_ridho_notifikasiTheme {
        MessageContent()
    }
}