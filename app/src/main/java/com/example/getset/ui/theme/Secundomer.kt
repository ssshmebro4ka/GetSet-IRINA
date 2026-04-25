package com.example.getset.ui.theme

import androidx.compose.material3.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.getset.R
import com.example.getset.painter
import kotlinx.coroutines.delay

@Composable
fun Secundomer(){
    var timeInSeconds by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    LaunchedEffect(isRunning) {
        while(isRunning){
            delay(1000L)
            timeInSeconds++
        }
    }
    val minutes= timeInSeconds/60
    val seconds=timeInSeconds%60
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Surface (
            modifier = Modifier.size(200.dp),
            shape = CircleShape,
            color = Color(0xFFF117C00) ,
            shadowElevation = 4.dp
        ){
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = String.format("%02d", minutes),
                    fontSize = 36.sp,
                    color = Color.White
                )
                Text(
                    text = "мин",
                    fontSize = 14.sp,
                    color =Color.White.copy(alpha = 0.7f)
                )
                Text(
                    text = String.format("%02d", seconds),
                    fontSize = 48.sp,
                    color = Color.White
                )
                Text(
                    text ="сек",
                    fontSize = 14.sp,
                    color =Color.White.copy(alpha=0.7f)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)){
            FloatingActionButton(
                onClick = {
                    isRunning=false
                    timeInSeconds=0
                },
                containerColor = Color(0xFFFE7F4D2)
            ) {
                Icon(
                    painterResource(R.drawable.refresh),
                    contentDescription = "Сброс",
                    tint = Color(0xFFF117C00)

                )
            }
            FloatingActionButton(
                onClick = {isRunning =! isRunning},
                containerColor = if(!isRunning) Color(0xFFFE7F4D2) else Color(0xFFF117C00)
            ) {
                Icon(
                    painter = if (!isRunning){
                        painterResource(R.drawable.play)
                    }else{
                        painterResource(R.drawable.pause2)
                    },
                    contentDescription = if(!isRunning) "Старт" else "Пауза",
                    tint = if (!isRunning) Color(0xFFF117C00) else Color.White
                )
            }
        }
    }
}