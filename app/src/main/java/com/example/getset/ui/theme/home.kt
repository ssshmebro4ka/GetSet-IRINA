package com.example.getset.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.getset.R

@Composable
fun HomeScreen(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Главная", "Я")//navigation bar
    val selectedIcons = listOf(
        painterResource(id = R.drawable.home),
        painterResource(id = R.drawable.i)
    )
    val unselectedIcons = listOf(
        painterResource(id = R.drawable.home),
        painterResource(id = R.drawable.i)
    )
    Scaffold(
        bottomBar = {NavigationBar (containerColor = Color(0xFFF117C00)){
            items.forEachIndexed { index, item -> NavigationBarItem(
                icon = {
                    Icon(
                    painter = if(selectedItem==index) selectedIcons[index] else unselectedIcons[index],
                    contentDescription = item,
                    modifier = Modifier.size(24.dp),
                        tint = if (selectedItem == index) Color.White else Color(0xFFF79A326)
                )
                },
                label = {
                    Text(
                        text = item,
                        color = if (selectedItem == index) Color.White else Color(0xFFF79A326)
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    selectedItem=index
                    when(index){
                        0->{}
                        1->{
                            navController.navigate(Screen.Profile.route)
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White,
                    selectedTextColor = Color(0xFFF117C00),
                    unselectedTextColor = Color.White,
                    indicatorColor = Color.Transparent)
                )
            }
        }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(24.dp)
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "GetSet",
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF117C00),
                modifier = Modifier
                    .fillMaxWidth()

            )
            Text(
                text = "Здравствуйте, Login",
                fontSize = 35.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFF117C00),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {navController.navigate(Screen.MyReadyTrain.route)},
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
                ) {
                    Text(
                        text = "Готовые тренировки",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        modifier = Modifier

                    )
                    Icon(
                        painter = painterResource(id = R.drawable.vector),
                        contentDescription = " ",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }

            }
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {navController.navigate(Screen.MyTrain.route)},
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
                ) {
                    Text(
                        text = "Мои тренировки",
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        modifier = Modifier

                    )
                    Icon(
                        painter = painterResource(id = R.drawable.vector),
                        contentDescription = " ",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {navController.navigate(Screen.Exersize.route)},
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
                ) {
                    Text(
                        text = "Упражнения",
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        modifier = Modifier

                    )
                    Icon(
                        painter = painterResource(id = R.drawable.vector),
                        contentDescription = " ",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {navController.navigate(Screen.MyPorpouseTrain.route)},
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
                ) {
                    Text(
                        text = "Мои достижения",
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        modifier = Modifier

                    )
                    Icon(
                        painter = painterResource(id = R.drawable.vector),
                        contentDescription = " ",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Button(
                onClick = {navController.navigate(Screen.Secundomer.route)},
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
                ) {
                    Text(
                        text = "Секундомер",
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        modifier = Modifier

                    )
                    Icon(
                        painter = painterResource(id = R.drawable.vector),
                        contentDescription = " ",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }


}