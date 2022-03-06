package ru.anasoft.nasa.view.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.anasoft.nasa.R
import ru.anasoft.nasa.databinding.FragmentSettingsBinding
import ru.anasoft.nasa.utils.KEY_SP_LOCAL
import ru.anasoft.nasa.utils.MY_THEME
import ru.anasoft.nasa.utils.themeActivityMain
import ru.anasoft.nasa.view.BaseFragment
import ru.anasoft.nasa.view.main.PictureOfTheDayFragment

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when(themeActivityMain) {
            1 -> { binding.radioButtonTheme1.isChecked = true }
            2 -> { binding.radioButtonTheme2.isChecked = true }
            3 -> { binding.radioButtonTheme3.isChecked = true }
        }
        initButtonOK()
    }

    private fun initButtonOK() {
        binding.buttonOK.setOnClickListener {
            themeActivityMain = myChoice
            saveMyTheme()
            requireActivity().recreate()
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commit()
        }
    }

    private val myChoice: Int
        get() {
            when (binding.radioGroupTheme.checkedRadioButtonId) {
                R.id.radioButtonTheme1 -> return 1
                R.id.radioButtonTheme2 -> return 2
                R.id.radioButtonTheme3 -> return 3
            }
            return themeActivityMain
        }

    private fun saveMyTheme() {
        val sharedPreferences = requireActivity().getSharedPreferences(KEY_SP_LOCAL, AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(MY_THEME, themeActivityMain)
        editor.apply()
    }
}