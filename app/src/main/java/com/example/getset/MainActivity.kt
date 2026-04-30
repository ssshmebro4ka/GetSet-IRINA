package com.example.getset


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.getset.ui.theme.AppNavigation
import com.example.getset.ui.theme.DataB
import com.example.getset.ui.theme.GetSetTheme
import com.example.getset.ui.theme.Screen


lateinit var painter: Painter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GetSetTheme {
                AppNavigation()
                //val navController = rememberNavController()
                //GetSetScreen(navController = navController)
                //SignInScreen(navController = navController)
                //MyPurpose(navController = navController)
                //Warning(navController = navController)
                //DataB(navController = navController)
                //HomeScreen(navController = navController)
                //IScreen(navController = navController)
                //MyChangePurpose(navController = navController)
                //ChangeWarning(navController = navController)
                //DataBCh (navController = navController)
                //Secundomer(navController = navController)
            }
        }
    }
}

@SuppressLint("InvalidColorHexValue")
@Composable
fun GetSetScreen(navController: NavHostController) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isNavigating by remember { mutableStateOf(false) }
    val isFormValid by remember {
        derivedStateOf {
            login.isNotBlank() && password.isNotBlank()
        }
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column (modifier = Modifier
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
                textAlign = TextAlign.Center
            )

            Text(
                text = "Регистрация",
                fontSize = 32.sp,
                fontWeight= FontWeight.Bold,
                color= Color(0xFFF117C00),
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
                    focusedContainerColor  = Color(0xFFFA1D05A),
                    focusedLabelColor = Color(0xFFF117C00),
                    unfocusedLabelColor = Color(0xFFF117C00),
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
                    focusedContainerColor  = Color(0xFFFA1D05A),
                    focusedLabelColor = Color(0xFFF117C00),
                    unfocusedLabelColor = Color(0xFFF117C00),
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
                    navController.navigate(Screen.MyPurpose.route){
                        popUpTo(Screen.Registration.route) { inclusive = true }
                    }
                }
            },
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors= ButtonDefaults.buttonColors(
                    containerColor = if (isFormValid) Color(0xFFF117C00) else Color (0xFFFB7D092),
                    contentColor = if(isFormValid) Color(0xFFFFFFEFE) else Color(color = 0xFFF117C00) ,
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
                fontSize =20.sp,
                color=Color(0xFFF117C00)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {navController.navigate(Screen.SignIn.route) {
            }},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp), //эщкере
                colors= ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF117C00),
                    contentColor = Color(0xFFFFFFEFE)
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
