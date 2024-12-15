package com.example.volleyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.volleyapp.ui.theme.VolleyAppTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VolleyAppTheme {
                var showSplash by remember { mutableStateOf(true) }

                if (showSplash) {
                    SplashScreenWithNavigation { showSplash = false }
                } else {
                    VolleyballScoreboard()
                }
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFACD)), // Cambia el color de fondo
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.icono),
                contentDescription = "Logo de la aplicación",
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = "VolleyApp",
                color = Color.Black,
                fontSize = 32.sp,
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Composable
fun SplashScreenWithNavigation(onSplashFinished: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000) // Tiempo en milisegundos
        onSplashFinished()
    }
    SplashScreen()
}


@Composable
fun VolleyballScoreboard(modifier: Modifier = Modifier) {
    var teamList by remember { mutableStateOf(listOf<Pair<String, Int>>()) }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFE0F7FA))
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Marcador Volleyball",
            fontSize = 37.sp,
            fontFamily = FontFamily.SansSerif
        )

        Image(
            painter = painterResource(id = R.drawable.fondo),
            contentDescription = "Imagen de voleibol",
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.Fit
        )

        // Mostrar equipos dinámicamente
        teamList.forEachIndexed { index, (name, score) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = name, fontSize = 28.sp, fontFamily = FontFamily.Monospace)
                Text(
                    text = score.toString(),
                    fontSize = 48.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.Monospace
                )
                Button(onClick = {
                    teamList = teamList.toMutableList().apply {
                        this[index] = this[index].copy(second = score + 1)
                    }
                },colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green
                )) {
                    Text("+1")
                }
            }
        }

        // Botón flotante para agregar equipos
        FloatingActionButton(
            onClick = { showDialog = true },
            containerColor = Color.Blue,
            contentColor = Color.White
        ) {
            Icon(
                Icons.Filled.Add,
                contentDescription = "Agregar equipo",
                modifier = Modifier.size(30.dp)
            )
        }

        // Mostrar el diálogo cuando showDialog es true
        if (showDialog) {
            AddTeamDialog(
                onAddTeam = { teamName ->
                    if (teamName.isNotBlank()) {
                        teamList = teamList + (teamName to 0)
                    }
                    showDialog = false
                },
                onDismiss = { showDialog = false }
            )
        }
    }
}

@Composable
fun AddTeamDialog(onAddTeam: (String) -> Unit, onDismiss: () -> Unit) {
    var teamName by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Agregar Equipo") },
        text = {
            Column {
                Text("Escribe el nombre del equipo:")
                TextField(
                    value = teamName,
                    onValueChange = { teamName = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onAddTeam(teamName) }) {
                Text("Agregar")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancelar")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun VolleyballScoreboardPreview() {
    VolleyAppTheme {
        VolleyballScoreboard()
    }
}