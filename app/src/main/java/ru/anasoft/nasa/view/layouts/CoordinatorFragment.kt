package ru.anasoft.nasa.view.layouts

import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import ru.anasoft.nasa.databinding.FragmentCoordinatorBinding
import ru.anasoft.nasa.databinding.FragmentEarthBinding
import ru.anasoft.nasa.view.BaseFragment

class CoordinatorFragment : BaseFragment<FragmentCoordinatorBinding>(FragmentCoordinatorBinding::inflate) {

    companion object {
        @JvmStatic
        fun newInstance() = CoordinatorFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val behavior = ButtonBehavior(requireContext())
        (binding.myButton.layoutParams as CoordinatorLayout.LayoutParams).behavior = behavior
    }
}