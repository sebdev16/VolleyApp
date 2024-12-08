package com.example.volleyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.volleyapp.ui.theme.VolleyAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VolleyAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    VolleyballScoreboard(Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun VolleyballScoreboard(modifier: Modifier = Modifier) {
    // Estados para los puntajes
    var team1Score by remember { mutableStateOf(0) }
    var team2Score by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Nombres de los equipos
        Text("Equipo 1", fontSize = 24.sp)
        Text("Equipo 2", fontSize = 24.sp)

        // Marcadores
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = team1Score.toString(), fontSize = 48.sp, textAlign = TextAlign.Center)
            Text(text = team2Score.toString(), fontSize = 48.sp, textAlign = TextAlign.Center)
        }

        // Botones de control
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { team1Score++ }) {
                Text("+1 Equipo 1")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { team2Score++ }) {
                Text("+1 Equipo 2")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                team1Score = 0
                team2Score = 0
            }) {
                Text("Reiniciar Marcador")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VolleyballScoreboardPreview() {
    VolleyAppTheme {
        VolleyballScoreboard()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VolleyAppTheme {
        Greeting("Android")
    }
}