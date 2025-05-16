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
import kotlinx.coroutines.flow.asFlow
import kotlin.math.floor
import kotlin.math.round

enum class State(val rgb: Int) {
    BASE(0xfbfcff),
    NO(0x787c7e),
    YELLOW(0xc7b660),
    GREEN(0x6dab68)
}

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
    var randomWord by remember { mutableStateOf<String>("") }
    LaunchedEffect(null, { // LaunchedEffect чтобы каждое нажатие не было новое слово
        randomWord = words.random() // Выбираем рандом слово
    })

    val squares by remember { mutableStateOf<MutableList<State>>(mutableListOf(State.BASE))}
    repeat(29) {
        squares.add(State.BASE)
    }

    val userChar = if (keys.isNotEmpty()) keys[keys.lastIndex] else "F" // последняя буква
    val sigma = floor((keys.size / 5).toDouble()) // кол-во заполненных строк.

    if (sigma == keys.size.toDouble() / 5 && keys.isNotEmpty()) { // сравниваем кол-во заполненных строк с кол-вом букв
        val wordInLine = mutableListOf<Char>() // буквы в последней строке
        for (i in 1..5) { // цикл из 5 раз так как в строке 5 букв
            wordInLine.add(keys.reversed()[if (i > 0) i - 1 else i]) // добавялем буквы с нужными индексами
        }
        println("sosal? ${wordInLine.reversed()}") // выводим

        // сравниваем финальное слово в строке с выбранным словом.
        for (i: Char in randomWord.uppercase()) {
            wordInLine.forEach {
                if (i == it) {
                    squares[randomWord.indexOf(i) + 1] = State.YELLOW
                    println("${it} exists in word ${randomWord}. sosal?")
                }
            }
        }
    }

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


        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier.size(280.dp, 414.dp),
        ) {
            itemsIndexed(squares) { index, square ->
                Square(
                    state = square,
                    content = {
                        Text(
                            text = "${keys.getOrElse(index) { ' ' }}"
                        )
                    }
                )
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
                        charList.add(keyEvent.key.keyCode.toInt().toChar()) // ❌toChar() || ✔ toInt().toChar()
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
    content: @Composable () -> Unit,
    state: State = State.BASE
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .requiredSize(56.dp, 56.dp)
            .padding(3.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color(state.rgb))
            .border(2.dp, Color(0xffdee1e9), RoundedCornerShape(5.dp))
            .padding(3.dp)
    ) {
        content()
    }
}




