package ru.anasoft.nasa.view.layouts

import android.view.Menu
import android.view.MenuInflater
import ru.anasoft.nasa.R
import ru.anasoft.nasa.databinding.FragmentConstraintBinding
import ru.anasoft.nasa.databinding.FragmentEarthBinding
import ru.anasoft.nasa.view.BaseFragment

class ConstraintFragment : BaseFragment<FragmentConstraintBinding>(FragmentConstraintBinding::inflate) {

    companion object {
        @JvmStatic
        fun newInstance() = ConstraintFragment()
    }
}