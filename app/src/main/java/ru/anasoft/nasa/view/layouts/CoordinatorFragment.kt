package ru.anasoft.nasa.view.layouts

import ru.anasoft.nasa.databinding.FragmentCoordinatorBinding
import ru.anasoft.nasa.databinding.FragmentEarthBinding
import ru.anasoft.nasa.view.BaseFragment

class CoordinatorFragment : BaseFragment<FragmentCoordinatorBinding>(FragmentCoordinatorBinding::inflate) {

    companion object {
        @JvmStatic
        fun newInstance() = CoordinatorFragment()
    }
}