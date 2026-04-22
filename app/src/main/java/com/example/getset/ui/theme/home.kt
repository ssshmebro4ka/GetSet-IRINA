package com.example.getset.ui.theme

import android.R.attr.fontWeight
import android.R.attr.onClick
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.getset.R

@Composable
fun HomeScreen(){
    var selectedItem by remember { mutableStateOf(0) }
    val items=listOf("S")//navigation bar
    Column (
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Text(
            text = "GetSet",
            fontSize = 64.sp,
            fontWeight= FontWeight.Bold,
            color= Color(0xFFF117C00),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
        )
        Text(
            text = "Здравствуйте, Login",
            fontSize = 35.sp,
            fontWeight= FontWeight.Medium,
            color= Color(0xFFF117C00),
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF117C00)
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) { Text(
                text = "Готовые тренировки",
                fontSize = 28.sp,
                fontWeight= FontWeight.Medium,
                color= Color.White,
                modifier = Modifier

            )
                Icon(painter = painterResource(id = R.drawable.vector),
                    contentDescription = " ",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }

        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF117C00)
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) { Text(
                text = "Мои тренировки",
                fontSize = 28.sp,
                fontWeight= FontWeight.Medium,
                color= Color.White,
                modifier = Modifier

            )
                Icon(painter = painterResource(id = R.drawable.vector),
                    contentDescription = " ",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF117C00)
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) { Text(
                text = "Упражнения",
                fontSize = 28.sp,
                fontWeight= FontWeight.Medium,
                color= Color.White,
                modifier = Modifier

            )
                Icon(painter = painterResource(id = R.drawable.vector),
                    contentDescription = " ",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF117C00)
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) { Text(
                text = "Мои достижения",
                fontSize = 28.sp,
                fontWeight= FontWeight.Medium,
                color= Color.White,
                modifier = Modifier

            )
                Icon(painter = painterResource(id = R.drawable.vector),
                    contentDescription = " ",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF117C00)
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) { Text(
                text = "Секундомер",
                fontSize = 28.sp,
                fontWeight= FontWeight.Medium,
                color= Color.White,
                modifier = Modifier

            )
                Icon(painter = painterResource(id = R.drawable.vector),
                    contentDescription = " ",
                    modifier = Modifier.size(20.dp),
                    tint = Color.White
                )
            }
        }
    }
}