package ru.anasoft.nasa.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.anasoft.nasa.R
import ru.anasoft.nasa.databinding.ActivityMainBinding
import ru.anasoft.nasa.utils.KEY_SP_LOCAL
import ru.anasoft.nasa.utils.MY_THEME
import ru.anasoft.nasa.utils.themeActivityMain
import ru.anasoft.nasa.view.main.PictureOfTheDayFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        themeActivityMain = getMyTheme()
        when (themeActivityMain) {
            1 -> { setTheme(R.style.MyBlueTheme) }
            2 -> { setTheme(R.style.MyGreenTheme) }
            3 -> { setTheme(R.style.MyRedTheme) }
            else -> {
                themeActivityMain = 1
                setTheme(R.style.MyBlueTheme)
            }
        }
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commit()
        }
    }

    private fun getMyTheme(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP_LOCAL, MODE_PRIVATE)
        return sharedPreferences.getInt(MY_THEME, 1)
    }
}