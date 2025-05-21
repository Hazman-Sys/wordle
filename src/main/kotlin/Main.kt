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
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.floor
import kotlin.math.round

enum class State(val rgb: Color) {
    BASE(Color(251, 252, 255)),
    NO(Color(120, 124, 126)),
    YELLOW(Color(199, 182, 96)),
    GREEN(Color(109, 171, 104))
}

@Composable
fun App(keys: List<Char>) {
    val allWords = File("words.txt").readText().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase().trim() }.toSet()
    val wordsForLevels =
        File("top.txt").readText().split("\r\n", "\n")
            .filter { it.length == 5 }.map { it.uppercase() }.toList()
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
    val squares = remember { mutableStateListOf<State>() } // статы
    LaunchedEffect(null, { // LaunchedEffect чтобы каждое нажатие не было новое слово
        randomWord = words.random() // Выбираем рандом слово

        repeat(30) { // добавялем 30 детей (кубиков)
            squares.add(State.BASE)
        }
    })

    println(randomWord)

    val userChar = if (keys.isNotEmpty()) keys[keys.lastIndex] else "F" // последняя буква
    val sigma = floor((keys.size / 5).toDouble()) // кол-во заполненных строк.

    if (sigma == keys.size.toDouble() / 5 && keys.isNotEmpty()) { // сравниваем кол-во заполненных строк с кол-вом букв
        val wordInLine = mutableListOf<Char>() // буквы в последней строке
        for (i in 1..5) { // цикл из 5 раз так как в строке 5 букв
            wordInLine.add(keys.reversed()[i - 1]) // добавялем буквы с нужными индексами
        }
        wordInLine.reverse()
        println("sosal? ${wordInLine}") // выводим

        // сравниваем финальное слово в строке с выбранным словом.
        wordInLine.forEach { charr ->
            run { // "break continue in inline lambdas" не поддерживается, используем return@run
                randomWord.uppercase().forEach { guesswordchar ->
                    println("dvdfjkggfkhkfghklghghjgjjjjghjghjfj jjg jhgj gjghjgh--------------$charr")
                    if (squares[randomWord.indexOf(guesswordchar) + 1] != State.BASE) {
                        return@run // мы уже проверили эту букву, переходим к следующей букве
                    }
                    println(randomWord.indexOf(guesswordchar) + 1)
                    if (guesswordchar == charr) { // желтый если есть в слове
                        squares[randomWord.indexOf(guesswordchar) + 1] = State.YELLOW
                        println("${charr} exists in word ${randomWord}. sosal?")
                    } else { // серый если нет
                        squares[randomWord.indexOf(guesswordchar) + 1] = State.NO
                    }
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
            .background(state.rgb)
            .border(2.dp, Color(0xffdee1e9), RoundedCornerShape(5.dp))
            .padding(3.dp)
    ) {
        content()
    }
}




