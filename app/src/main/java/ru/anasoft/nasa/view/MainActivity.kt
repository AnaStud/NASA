package ru.anasoft.nasa.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.anasoft.nasa.R
import ru.anasoft.nasa.view.main.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commit()
        }
    }
}