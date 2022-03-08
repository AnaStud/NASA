package ru.anasoft.nasa.view.navigation

import android.view.Menu
import android.view.MenuInflater
import ru.anasoft.nasa.R
import ru.anasoft.nasa.databinding.FragmentEarthBinding
import ru.anasoft.nasa.view.BaseFragment

class EarthFragment : BaseFragment<FragmentEarthBinding>(FragmentEarthBinding::inflate) {

    companion object {
        @JvmStatic
        fun newInstance() = EarthFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }
}