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
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
    // Estados para los puntajes
    var team1Score by remember { mutableStateOf(0) }
    var team2Score by remember { mutableStateOf(0) }

    // Estados para los nombres de los equipos
    var team1Name by remember { mutableStateOf(TextFieldValue("Equipo 1")) }
    var team2Name by remember { mutableStateOf(TextFieldValue("Equipo 2")) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFE0F7FA))
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Marcador Volleyball", fontSize = 37.sp,
            fontFamily = FontFamily.SansSerif) // titulo de la app

        Image(
            painter = painterResource(id = R.drawable.fondo), //se la pasa la imagen
            contentDescription = "Imagen de voleibol",
            modifier = Modifier.size(200.dp), // Tamaño de la imagen
            contentScale = ContentScale.Fit
        )

        Column(modifier = Modifier.fillMaxWidth()) { // textfield para los nombres
            TextField(
                value = team1Name,
                onValueChange = { team1Name = it },
                label = { Text("Nombre del Equipo 1") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = team2Name,
                onValueChange = { team2Name = it },
                label = { Text("Nombre del Equipo 2") },
                modifier = Modifier.fillMaxWidth()
            )
        }
//----------------------------------------------------------------------------
        // Marcadores (muestra los nombres de los equipos y los puntajes)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = team1Name.text, fontSize = 28.sp, fontFamily = FontFamily.Monospace)
            Text(text = team1Score.toString(), fontSize = 48.sp, textAlign = TextAlign.Center, fontFamily = FontFamily.Monospace)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = team2Name.text, fontSize = 28.sp, color = Color.Black, fontFamily = FontFamily.Monospace)
            Text(text = team2Score.toString(), fontSize = 48.sp, color = Color.Black, fontFamily = FontFamily.Monospace)
        }
//-------------------------------------------------------------------------------------------------------

        // Botones de control
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically)
            {
                Button(onClick = { if (team1Score > 0) team1Score-- }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )) {

                    Text(text = "-1", fontSize = 19.sp)

                }

                Text(text = team1Name.text, fontSize = 22.sp)

                Button(onClick = { team1Score++ }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green
                )) {
                    Text(text = "+1", fontSize = 19.sp)
                }

            }

            Spacer(modifier = Modifier.height(50.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically)
            {
                Button(onClick = { if (team2Score > 0) team2Score-- }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )) {
                        Text(text = "-1", fontSize = 19.sp)
                }

                Text(text = team2Name.text, fontSize = 22.sp)

                Button(onClick = { team2Score++ }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green
                )) {
                    Text(text = "+1", fontSize = 19.sp)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            IconButton(onClick = { team1Score = 0
                team2Score = 0 }) {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = "Reiniciar Marcador",
                    tint = Color.Magenta,
                    modifier = Modifier.size(80.dp) //tamaño del icono
                )
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