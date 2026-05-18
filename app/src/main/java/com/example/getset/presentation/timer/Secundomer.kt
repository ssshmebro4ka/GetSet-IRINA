package com.example.getset.presentation.timer

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.getset.R
import kotlinx.coroutines.delay

@SuppressLint("DefaultLocale")
@Composable
fun Secundino(onBackClick: () -> Unit = {}, navController: NavHostController) {
    var timeInSeconds by remember { mutableIntStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    LaunchedEffect(isRunning) {
        while (isRunning) {
            delay(1000L)
            timeInSeconds++
        }
    }
    val minutes = timeInSeconds / 60
    val seconds = timeInSeconds % 60

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = " ",
                    tint = Color(0xFF117C00),
                    modifier = Modifier.size(45.dp)
                )
            }

        }
        Surface(
            modifier = Modifier.size(300.dp),
            shape = CircleShape,
            color = Color(0xFF117C00),
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = String.format("%02d", minutes),
                    fontSize = 60.sp,
                    color = Color.White
                )
                Text(
                    text = "мин",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Text(
                    text = String.format("%02d", seconds),
                    fontSize = 70.sp,
                    color = Color.White
                )
                Text(
                    text = "сек",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            FloatingActionButton(
                onClick = {
                    isRunning = false
                    timeInSeconds = 0
                },
                containerColor = Color(0xFFE7F4D2)
            ) {
                Icon(
                    painterResource(R.drawable.refresh),
                    contentDescription = "Сброс",
                    tint = Color(0xFF117C00)

                )
            }
            FloatingActionButton(
                onClick = { isRunning = !isRunning },
                containerColor = if (!isRunning) Color(0xFFE7F4D2) else Color(0xFF117C00)
            ) {
                Icon(
                    painter = if (!isRunning) {
                        painterResource(R.drawable.ic_play)
                    } else {
                        painterResource(R.drawable.pause2)
                    },
                    contentDescription = if (!isRunning) "Старт" else "Пауза",
                    tint = if (!isRunning) Color(0xFF117C00) else Color.White
                )
            }
        }
    }
}