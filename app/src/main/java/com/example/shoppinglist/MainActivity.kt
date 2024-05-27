package com.example.shoppinglist

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.shoppinglist.ui.theme.ShoppingListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShoppingListApp()
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun ShoppingListApp() {

    var selectedItemIndex: Int by  remember{
        mutableStateOf(
            -1
        )
    }
    val itemsList =
        mutableStateListOf<ShoppingItem>(
        )

Column(modifier=Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,) {
    var showDialog by remember {
        mutableStateOf(
            false
        )
    }
    fun createItem(item: ShoppingItem){
    itemsList.add(item)
    println("is show dia $showDialog")
}

    fun editItem(item: ShoppingItem, index:Int){
        itemsList[index]= item
    }
    ElevatedButton(  onClick = {
        selectedItemIndex=-1
        showDialog =true

    },
        modifier= Modifier
            .padding(Dp(20F))
            .height(Dp(50F),)
            .width
                (Dp(200F),),){

        if(showDialog){
            ShowItemDataDialog(setShowDialog = {
                showDialog = false
            }, action = {
                if (selectedItemIndex == -1) {
                    //create
                    createItem(it)

                } else {
                    editItem(it, selectedItemIndex)
                }
            }, item = if(selectedItemIndex==-1) null else
                itemsList[selectedItemIndex])
        }
        Text(text = "Add Item")
    }
    Spacer(modifier = Modifier.height(Dp(10F)))
    LazyColumn(modifier=Modifier.fillMaxSize()){
        itemsIndexed(itemsList) {index, it->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(Dp(50F))
                    .padding(horizontal = Dp(10F),),
                    colors = CardDefaults.cardColors(containerColor = Color.LightGray),
                    border = BorderStroke(Dp(.3F), Color.Gray),
                    content = {
                        Row(
                            modifier = Modifier.fillMaxSize(),

                            horizontalArrangement =
                            Arrangement
                                .SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                style = MaterialTheme.typography.headlineSmall,
                                text = it.name
                            )
                            Text(

                                text = "qty=${it.quantity}"
                            )
                            Row(
                                modifier = Modifier.width(Dp(80F)),
                                horizontalArrangement =
                                Arrangement
                                    .Start
                            ) {
                                IconButton(onClick = {
                                    selectedItemIndex =index
                                    showDialog =true
                                }) {

                                    Icon(
                                        Icons.Outlined.Edit,
                                        contentDescription = "edit"
                                    )


                                }
//                    Spacer(modifier = Modifier.width(Dp(10F),),)
                                IconButton(onClick = { /* do something */ }) {

                                    Icon(
                                        Icons.Outlined.Delete,
                                        contentDescription = "edit"
                                    )

                                }
                            }
                        }
                    }

                )
            }
        }
}

}



class ShoppingItem(val name:String, val quantity:Int){

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowItemDataDialog(setShowDialog: (Boolean)-> Unit, action:
(ShoppingItem ) -> Unit, item: ShoppingItem? ){
    val txtFieldError = remember { mutableStateOf("") }
    var name:String? by remember { mutableStateOf(item?.name) }
    var quantityString: String by remember { mutableStateOf(item?.quantity
        .toString()) }
    Dialog(onDismissRequest = {setShowDialog(false)},  properties =
    DialogProperties(
        dismissOnBackPress = true,
        dismissOnClickOutside = true
    )){
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {  Box(
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

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
                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    ElevatedButton(
                        onClick = {
                            if ( name.isNullOrEmpty() ||
                                quantityString.toIntOrNull() == null) {
                                txtFieldError.value = "Field can not be empty"

                            }
                else{

                action(ShoppingItem(name!!, quantityString.toInt()))
                               setShowDialog(false)
                }
                        },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = "Done")
                    }
                }
            }
        }
    }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoppingListTheme {
        ShoppingListApp()
    }
}
