package ru.anasoft.nasa.view.layouts

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import ru.anasoft.nasa.R
import ru.anasoft.nasa.databinding.ActivityLayoutsBinding
import ru.anasoft.nasa.utils.themeActivityMain

class LayoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLayoutsBinding

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

        if (Build.VERSION.RELEASE.toInt() > 10) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        binding = ActivityLayoutsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigationView()
    }

    private fun initBottomNavigationView() {

        with(binding) {
            bottomLayouts.selectedItemId = R.id.bottom_constraint
            navigationTo(ConstraintFragment())

            bottomLayouts.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.bottom_constraint -> {
                        navigationTo(ConstraintFragment())
                        true
                    }
                    R.id.bottom_coordinator -> {
                        navigationTo(CoordinatorFragment())
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun navigationTo(f: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.containerLayouts, f).commit()
    }

}