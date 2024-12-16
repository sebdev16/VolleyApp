package com.example.volleyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.graphicsLayer
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
import com.airbnb.lottie.compose.*

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
fun LottieAnimationExample() {
    // Carga el archivo de animación desde raw
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.volleyanime))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever // Repite la animación
    )

    // Mostrar la animación
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier
            .size(200.dp), // Tamaño de la animación
        contentScale = ContentScale.Crop
    )
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFACD)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Lottie Animation
            LottieAnimationExample()

            Spacer(modifier = Modifier.height(16.dp))

            // Título debajo de la animación
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
    var winningTeam by remember { mutableStateOf<String?>(null) } // Estado para el equipo ganador

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
                Button(
                    onClick = {
                        if (winningTeam == null) { // Evitar sumar puntos si ya hay un ganador
                            teamList = teamList.toMutableList().apply {
                                val newScore = score + 1
                                this[index] = this[index].copy(second = newScore)

                                // Verificar si el equipo ha llegado a 25 puntos
                                if (newScore >= 25) {
                                    winningTeam = name
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green
                    )
                ) {
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

        // Mostrar el anuncio del ganador cuando winningTeam no sea nulo
        if (winningTeam != null) {
            AlertDialog(
                onDismissRequest = { winningTeam = null },
                title = { Text("¡Tenemos un ganador!") },
                text = { Text("El equipo \"$winningTeam\" ha ganado el partido.") },
                confirmButton = {
                    Button(onClick = {
                        // Reiniciar la lista de equipos y el estado del ganador
                        teamList = listOf()
                        winningTeam = null
                    }) {
                        Text("Reiniciar Juego")
                    }
                },
                dismissButton = {
                    Button(onClick = { winningTeam = null }) {
                        Text("Cerrar")
                    }
                }
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

@Composable
fun AnimatedFloatingActionButton() {
    var isClicked by remember { mutableStateOf(false) }
    val size by animateFloatAsState(targetValue = if (isClicked) 72f else 56f) // Cambio de tamaño animado

    FloatingActionButton(
        onClick = { isClicked = !isClicked }, // Cambia el estado al hacer clic
        modifier = Modifier.size(size.dp), // Aplicamos el tamaño animado
        containerColor = Color.Blue,
        contentColor = Color.White
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Agregar equipo")
    }
}


@Preview(showBackground = true)
@Composable
fun VolleyballScoreboardPreview() {
    VolleyAppTheme {
        VolleyballScoreboard()
    }
}