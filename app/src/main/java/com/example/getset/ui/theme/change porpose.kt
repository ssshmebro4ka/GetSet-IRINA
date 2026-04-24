package com.example.getset.ui.theme

import com.example.getset.R



import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

@SuppressLint("InvalidColorHexValue")
@Composable
fun MyChangePurpose(onBackClick:()-> Unit={}){
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
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(onClick = onBackClick,
                modifier = Modifier.size(48.dp)) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = " ",
                    tint = Color(0xFFF117C00),
                    modifier = Modifier.size(45.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
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
        Text(text="Выберите новую цель",
            fontSize = 30.sp,
            fontWeight= FontWeight.Medium,
            color= Color(0xFFF117C00),
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        SelectableButtonCh(text="Стать сильнее",
            isSelected= isOption1Selected,
            onClick={isOption1Selected=!isOption1Selected},
        )
        Spacer(modifier = Modifier.height(20.dp))
        SelectableButtonCh(text="Улучшить здоровье",
            isSelected= isOption2Selected,
            onClick={isOption2Selected=!isOption2Selected}
        )
        Spacer(modifier = Modifier.height(20.dp))
        SelectableButtonCh(text="Сбросить вес",
            isSelected= isOption3Selected,
            onClick={isOption3Selected=!isOption3Selected}
        )
        Spacer(modifier = Modifier.height(20.dp))
        SelectableButtonCh(text="Стать стройным и рельефным",
            isSelected= isOption4Selected,
            onClick={isOption4Selected=!isOption4Selected}
        )
        Spacer(modifier = Modifier.height(20.dp))
        SelectableButtonCh(text="Набрать мышечную массу",
            isSelected= isOption5Selected,
            onClick={isOption5Selected=!isOption5Selected}
        )
        Spacer(modifier = Modifier.height(48.dp))
        Button(onClick = {},
            enabled = isOneChoose,
            modifier = Modifier.fillMaxWidth(1f)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor =  Color(0xFFF117C00),
                disabledContentColor = Color(0xFFF117C00),
                contentColor = Color.White,
                disabledContainerColor = Color(0xFFFB7D092)
            )
        ) {
            Text(
                text = "Сохранить",
                fontSize = 20.sp)
        }
    }
}

@Composable
fun SelectableButtonCh(text: String,
                     isSelected: Boolean,
                     onClick: () -> Unit,
                     modifier: Modifier= Modifier
) {
    Button(onClick=onClick,
        modifier = Modifier.fillMaxWidth(1f)
            .height(80.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if(isSelected) Color(0xFFF117C00) else Color(0xFFFB7D092),

            ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 4.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                if (isSelected){
                    Icon(
                        painter = painterResource(id = R.drawable.group),
                        contentDescription = " ",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White

                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(text,
                    fontSize=20.sp,
                    color=if(isSelected) Color.White else Color(0xFFF117C00)
                )
            }
        }

    }

}
