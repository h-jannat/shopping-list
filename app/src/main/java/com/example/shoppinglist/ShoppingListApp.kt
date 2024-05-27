package com.example.shoppinglist

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

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

    Column(modifier= Modifier.fillMaxSize(),
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
        fun deleteItem( index:Int){
            itemsList.removeAt(index)
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
        LazyColumn(modifier= Modifier.fillMaxSize()){
            itemsIndexed(itemsList) {index, it->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(Dp(70F))
                    .padding(horizontal = 5.dp, vertical = 5.dp).padding(5
                        .dp),
                    colors = CardDefaults.cardColors(containerColor = Color
                       .White),
                    border = BorderStroke(Dp(.3F), Color.LightGray),
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
                                IconButton(onClick = { deleteItem(index)
                                }) {

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
