import androidx.compose.material.MaterialTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import dev.programadorthi.common.App

fun main() = singleWindowApplication(
    title = "Norris Facts",
    state = WindowState(width = 640.dp, height = 360.dp)
) {
    MaterialTheme {
        App()
    }
}