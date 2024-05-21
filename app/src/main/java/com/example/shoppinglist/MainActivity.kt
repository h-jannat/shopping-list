package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

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
                    ShoppingListApp("Android")
                }
            }
        }
    }
}




@Composable
fun ShoppingListApp(name: String, modifier: Modifier = Modifier) {
    var itemsList by  remember{
        mutableStateOf(
            listOf<ShoppingItem>(ShoppingItem(name= "apple",
            quantity = 2))
        )
     }
Column(modifier=Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,) {
    ElevatedButton(  onClick = {},
        modifier= Modifier.padding(Dp(20F))
        .height(Dp(50F),)
        .width
            (Dp(200F),),){
        Text(text = "Add Item")
    }
    Spacer(modifier = Modifier.height(Dp(10F)))
    LazyColumn(modifier=Modifier.fillMaxSize()){
        items(itemsList) {
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
                                IconButton(onClick = { /* do something */ }) {

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


@Composable
fun PopupBox(popupWidth: Float, popupHeight:Float, showPopup: Boolean, onClickOutside: () -> Unit, content: @Composable() () -> Unit) {

    if (showPopup) {
        // full screen background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Green)
                .zIndex(10F),
            contentAlignment = Alignment.Center
        ) {
            // popup
            Popup(
                alignment = Alignment.Center,
                properties = PopupProperties(
                    excludeFromSystemGesture = true,
                ),
                // to dismiss on click outside
                onDismissRequest = { onClickOutside() }
            ) {
                Box(
                    Modifier
                        .width(Dp(popupWidth))
                        .height(Dp(popupHeight))
                        .background(Color.White)
                        .clip(RoundedCornerShape(4)),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    }
}

class ShoppingItem(val name:String, val quantity:Int){

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoppingListTheme {
        ShoppingListApp("Android")
    }
}
