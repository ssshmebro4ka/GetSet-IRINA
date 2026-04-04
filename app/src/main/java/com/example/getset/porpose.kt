package com.example.getset

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Button
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.res.painterResource

@SuppressLint("InvalidColorHexValue")
@Composable
fun MyPurpose(){
    var isOption1Selected  by remember { mutableStateOf(false) }
    var isOption2Selected  by remember { mutableStateOf(false) }
    var isOption3Selected  by remember { mutableStateOf(false) }
    var isOption4Selected  by remember { mutableStateOf(false) }
    var isOption5Selected  by remember { mutableStateOf(false) }
    val isOneChoose= (isOption1Selected ||isOption2Selected ||isOption3Selected||isOption4Selected||isOption5Selected)
    Column (modifier = Modifier
        .background(androidx.compose.ui.graphics.Color.White)
        .fillMaxSize()
        .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Text(text="GetSet",
            fontSize = 64.sp,
            fontWeight= FontWeight.Bold,
            color= Color(0xFFF117C00),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(text="Выберите цель",
            fontSize = 36.sp,
            fontWeight= FontWeight.Medium,
            color= Color(0xFFF117C00),
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        SelectableButton(text="Стать сильнее",
            isSelected= isOption1Selected,
            onClick={isOption1Selected=!isOption1Selected}
        )
        Spacer(modifier = Modifier.height(10.dp))
        SelectableButton(text="Улучшить здоровье",
            isSelected= isOption2Selected,
            onClick={isOption2Selected=!isOption2Selected}
        )
        Spacer(modifier = Modifier.height(10.dp))
        SelectableButton(text="Сбросить вес",
            isSelected= isOption3Selected,
            onClick={isOption3Selected=!isOption3Selected}
        )
        Spacer(modifier = Modifier.height(10.dp))
        SelectableButton(text="Стать стройным и рельефным",
            isSelected= isOption4Selected,
            onClick={isOption4Selected=!isOption4Selected}
        )
        Spacer(modifier = Modifier.height(10.dp))
        SelectableButton(text="Набрать мышечную массу",
            isSelected= isOption5Selected,
            onClick={isOption5Selected=!isOption5Selected}
        )
    }
}

@Composable
fun SelectableButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(onClick=onClick,
        modifier = Modifier.fillMaxWidth(0.8f),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if(isSelected) Color(0xFFF117C00) else Color(0xFF9E9E9E),
            disabledContentColor = Color(0xFF9E9E9E)
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 4.dp
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            if (isSelected){
                Icon(
                    painter = painterResource(id = android.R.drawable.checkbox_on_background),
                    contentDescription = "djcnksdncsbd",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(text,fontSize=16.sp)
        }
    }
}
