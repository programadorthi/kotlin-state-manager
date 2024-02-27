package dev.programadorthi.android

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dev.programadorthi.state.core.androidValueManager

class MainActivity : AppCompatActivity() {
    private val model: MainViewModel by viewModels()

    private var year by androidValueManager(2024)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*setContent {
            MaterialTheme {
                App()
            }
        }*/

        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.nameText)
        val decrementButton = findViewById<Button>(R.id.decrementButton)
        val incrementButton = findViewById<Button>(R.id.incrementButton)
        val update = {
            textView.text = model.name + " - " + year
            decrementButton.text = "Decrement ${model.age}"
            incrementButton.text = "Increment ${model.age}"
        }
        update()

        decrementButton.setOnClickListener {
            year--
            model.decrement()
            update()
        }

        incrementButton.setOnClickListener {
            year++
            model.increment()
            update()
        }
    }
}