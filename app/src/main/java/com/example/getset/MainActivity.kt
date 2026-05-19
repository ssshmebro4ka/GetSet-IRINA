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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.getset.ui.theme.AppNavigation
import com.example.getset.ui.theme.Database_Helper
import com.example.getset.ui.theme.GetSetTheme
import com.example.getset.ui.theme.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext




class MainViewModel(private val dbHelper: Database_Helper) : ViewModel() {

    private val _dataList = MutableStateFlow<List<Map<String, Any>>>(emptyList())
    val dataList: StateFlow<List<Map<String, Any>>> = _dataList

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = dbHelper.loadDataToList()
                _dataList.update { data }
                println("Загружено ${data.size} упражнений")
            } catch (e: Exception) {
                println("Ошибка: ${e.message}")
                _dataList.update { emptyList() }
            }
        }
    }
}
class MainActivity : ComponentActivity() {

    private lateinit var dbHelper: Database_Helper
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = Database_Helper(this)
        viewModel = MainViewModel(dbHelper)
        viewModel.loadData()
        lifecycleScope.launch {
            try {
                viewModel.loadData()
            } catch (e: Exception) {
                println("Ошибка загрузки данных: ${e.message}")
            }
        }
        enableEdgeToEdge()

        setContent {
            GetSetTheme {
                AppNavigation(viewModel = viewModel)
                val navController = rememberNavController()
                //ExercisesScreen(navController = navController,
                 //               viewModel = viewModel
                //)
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
    var errorMessage by remember { mutableStateOf("") }
    val isFormValid by remember {
        derivedStateOf {
            login.isNotBlank() && password.isNotBlank()
        }
    }
    val auth = Firebase.auth
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
                label={ Text("Почта", fontSize = 20.sp)},
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
                    auth.createUserWithEmailAndPassword(login, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                navController.navigate(Screen.MyPurpose.route) {
                                    popUpTo(Screen.Registration.route) { inclusive = true }
                                }
                            } else {
                                errorMessage = task.exception?.localizedMessage ?: "Ошибка регистрации"
                            }
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
            if (errorMessage.isNotBlank()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
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
