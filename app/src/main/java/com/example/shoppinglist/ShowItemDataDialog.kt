package com.example.shoppinglist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowItemDataDialog(setShowDialog: (Boolean)-> Unit, action:
    (ShoppingItem ) -> Unit, item: ShoppingItem? ){

    var name:String? by remember { mutableStateOf(item?.name) }
    var quantityString: String by remember { mutableStateOf(item?.quantity
        .toString()) }
    Dialog(onDismissRequest = {setShowDialog(false)},  properties =
    DialogProperties(
        dismissOnBackPress = true,
        dismissOnClickOutside = true
    )
    ){
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            border = BorderStroke(Dp(.3F), Color.LightGray),
        ) {  Box(
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                var error:String by remember { mutableStateOf("") }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Set value",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold
                        )
                    )

                }
                Spacer(modifier = Modifier.height(20.dp))
                if(error.isNotEmpty()) Text(
                    text = error,
                    style = TextStyle(
                        fontFamily = FontFamily.Default,
                        color = Color.Red
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = name?:"",
                    onValueChange = { name = it },
                    label = { Text("name") },
                    keyboardOptions =
                    KeyboardOptions(keyboardType = KeyboardType.Text),
                )



                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = if(quantityString=="null") "" else quantityString,
                    onValueChange = { quantityString = it},
                    label = { Text("quantity") },
                    keyboardOptions =
                    KeyboardOptions(keyboardType = KeyboardType.Number),
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier= Modifier.fillMaxWidth(),
                   horizontalArrangement = Arrangement.Center
                 ) {
                    val buttonModifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                    ElevatedButton(
                        onClick = {
                            if ( name.isNullOrEmpty() ||
                                quantityString.toIntOrNull() == null) {
                                error= "Make sure to enter valid values"

                            }
                            else{

                                action(ShoppingItem(name!!, quantityString.toInt()))
                                setShowDialog(false)
                            }
                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier = buttonModifier
                    ) {
                        Text(text = "Done")
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    ElevatedButton(
                        onClick = {
                                setShowDialog(false)
                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier =  buttonModifier
                    ) {
                        Text(text = "Cancel")
                    }
                }
            }
        }
        }
    }
}

