package ru.anasoft.nasa.view.navigation

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import ru.anasoft.nasa.R
import ru.anasoft.nasa.databinding.ActivityBottomNavigationBinding
import ru.anasoft.nasa.databinding.ActivityMainBinding
import ru.anasoft.nasa.utils.themeActivityMain

class BottomNavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (themeActivityMain) {
            1 -> { setTheme(R.style.MyBlueTheme) }
            2 -> { setTheme(R.style.MyGreenTheme) }
            3 -> { setTheme(R.style.MyRedTheme) }
            else -> {
                themeActivityMain = 1
                setTheme(R.style.MyBlueTheme)
            }
        }

//        if (Build.VERSION.RELEASE.toInt() > 10) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        }

        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {

        with(binding) {
            bottomNavigationView.selectedItemId = R.id.bottom_view_earth
            navigationTo(EarthFragment())

            bottomNavigationView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.bottom_view_earth -> {
                        navigationTo(EarthFragment())
                        true
                    }
                    R.id.bottom_view_mars -> {
                        navigationTo(MarsFragment())
                        true
                    }
                    R.id.bottom_view_system -> {
                        navigationTo(SystemFragment())
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun navigationTo(f: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, f).commit()
    }

}