package ru.anasoft.nasa.view.navigation

import android.view.Menu
import android.view.MenuInflater
import ru.anasoft.nasa.R
import ru.anasoft.nasa.databinding.FragmentSystemBinding
import ru.anasoft.nasa.view.BaseFragment

class SystemFragment : BaseFragment<FragmentSystemBinding>(FragmentSystemBinding::inflate) {

    companion object {
        @JvmStatic
        fun newInstance() = SystemFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }
}