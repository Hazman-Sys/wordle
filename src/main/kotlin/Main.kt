import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun App(key: Char) {
    println(key)

    val words = listOf(
        "Igloo",
        "Sigma",
        "House",
        "World",
        "Super",
        "Class",
        "Robot",
        "Salve",
        "Flank",
        "Gauge",
        "Gusto",
        "Carom",
        "Ouija"
    )
    val randomWord = words.random()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .onKeyEvent {
                println(Char(it.key.keyCode.toInt()))
                false
            }
    ) {

        val squaresList = (0..29).toList()
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier.size(280.dp, 414.dp),
        ) {
            items(squaresList) { square ->


//---------------------------------------------------------------------------------------------------
                var textState by remember { mutableStateOf(TextFieldValue("")) }

                Square(

                ) {
                    Text(
                        text = "$key"
                    )
                }
            }
        }
    }
}


fun main() = application {
    var key by remember {
        mutableStateOf(' ')
    }
    Window(
        onCloseRequest = ::exitApplication,
        onKeyEvent = { keyEvent ->
            if(keyEvent.type == KeyEventType.KeyUp){
                key = keyEvent.key.keyCode.toChar()
            }
            false
        }
    ) {
        //App()
        MaterialTheme {
            Surface { App(
                key = key
            ) }
        }

    }
}

@Composable
fun Square(
    content: @Composable () -> Unit
) {
    var color by remember {  mutableStateOf(Color(0xfbfcff)) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .requiredSize(56.dp, 56.dp)
            .padding(3.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(color)
            .border(2.dp, Color(0xffdee1e9), RoundedCornerShape(5.dp))
            .padding(3.dp)
    ) {
        content()
    }
}




