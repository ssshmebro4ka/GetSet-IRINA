package com.example.getset

import SignInScreen
import android.R.attr.contentDescription
import android.R.attr.enabled
import android.R.attr.fontWeight
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.getset.ui.theme.GetSetTheme
import java.lang.Compiler.enable

lateinit var painter: Painter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GetSetTheme {
                GetSetScreen()
                ///SignInScreen()
            }
        }
    }
}

@SuppressLint("InvalidColorHexValue")
@Composable
fun GetSetScreen(){
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isFormValid by remember {
        derivedStateOf {
            login.isNotBlank() && password.isNotBlank()
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Image(painter = painterResource(id=R.drawable.background1),
            "background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column (modifier = Modifier
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
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))
            Text(
                text = "Регистрация",
                fontSize = 32.sp,
                fontWeight= FontWeight.Bold,
                color= Color(0xFFF11BA805),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(48.dp))
            OutlinedTextField(
                value = login,
                onValueChange = {login=it},
                label={ Text("Логин", fontSize = 20.sp)},
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFFE7F4D2),
                    focusedContainerColor  = Color(0xFFFC5FC73),
                    focusedLabelColor = Color(0xFFF117C00),
                    unfocusedLabelColor = Color(0xFFF5ED739),
                    focusedBorderColor = Color(0xFFF117C00),
                    unfocusedBorderColor = Color(0xFFF117C00)
                ),
                shape= RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(40.dp))
            OutlinedTextField(
                value = password,
                onValueChange = {password=it},
                label={Text("Пароль", fontSize = 20.sp)},
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFFE7F4D2),
                    focusedContainerColor  = Color(0xFFFC5FC73),
                    focusedLabelColor = Color(0xFFF117C00),
                    unfocusedLabelColor = Color(0xFFF5ED739),
                    focusedBorderColor = Color(0xFFF117C00),
                    unfocusedBorderColor = Color(0xFFF117C00)
                ),
                shape= RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(onClick = {
                if(isFormValid){
                    println("Регистрация:$login/$password")
                }
            },
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors= ButtonDefaults.buttonColors(
                    containerColor = if (isFormValid) Color(0xFFF117C00) else Color (0xFFFB7D092),
                    contentColor = if(isFormValid) Color(0xFFFB7D092) else Color(color = 0xFFF117C00) ,
                    disabledContainerColor = Color(0xFFFB7D092),
                    disabledContentColor = Color(0xFFF117C00)
                ),

            )
            {
                Text(text="Зарегистрироваться",
                    fontSize =20.sp)
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(text="Уже есть аккаунт ?",
                fontSize =18.sp,
                color=Color(0xFFF117C00)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors= ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF117C00),
                    contentColor = Color(0xFFFB7D092)
                )
            )
            {
                Text("Войти",
                    fontSize =20.sp
                )
            }
        }
    }
}
