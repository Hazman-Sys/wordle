import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
fun App(keys: List<Char>) {

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
    val randomWord = words.random() // Выбираем рандом слово

    val userChar = 'I'


    randomWord.find {
        it == userChar // Сравниваем слово пользователя с выбранным словом
    }
    randomWord[0] == userChar // зеленое если совпадает

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

        val squaresList = (0..29).toList() // Блоки

        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier.size(280.dp, 414.dp),
        ) {
            itemsIndexed(squaresList) { index, square ->
//---------------------------------------------------------------------------------------------------//
                var textState by remember { mutableStateOf(TextFieldValue("")) }

                Square(

                ) {
                    Text(
                        text = "${keys.getOrElse(index) { ' ' }}"
                    )
                }
            }
        }
    }

}


fun main() = application {
    val charList = mutableStateListOf<Char>()
    var index by remember { mutableStateOf(0) } // переход автоматом на следующую строку
    var key by remember {
        mutableStateOf(' ')
    }
    Window(
        onCloseRequest = ::exitApplication,
        onKeyEvent = { keyEvent ->
            if (keyEvent.key == Key.Enter) {
                println("Enter")
            } else {
                if (keyEvent.key == Key.Backspace) {
                    println("BackSpace")       // Запрещаем использовать Enter и BackSpace
                } else {
                    if (keyEvent.type == KeyEventType.KeyUp) {
                        charList.add(keyEvent.key.keyCode.toChar())
                    }
                }
            }

            false
        }
    ) {
        //App()
        MaterialTheme {
            Surface {
                App(
                    keys = charList
                )
            }
        }

    }
}

// ----------------------------------------------------------------------------------------------- //
@Composable
fun Square(
    content: @Composable () -> Unit
) {
    var color by remember { mutableStateOf(Color(0xfbfcff)) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .requiredSize(56.dp, 56.dp)
            .padding(3.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(color)                      // Свойства блока ( настройка )
            .border(2.dp, Color(0xffdee1e9), RoundedCornerShape(5.dp))
            .padding(3.dp)
    ) {
        content()
    }
}




