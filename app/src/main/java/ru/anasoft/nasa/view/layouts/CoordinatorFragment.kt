package ru.anasoft.nasa.view.layouts

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import ru.anasoft.nasa.R
import ru.anasoft.nasa.databinding.FragmentCoordinatorBinding
import ru.anasoft.nasa.view.BaseFragment

class CoordinatorFragment : BaseFragment<FragmentCoordinatorBinding>(FragmentCoordinatorBinding::inflate) {

    private var currentColor = 7
    private lateinit var spannableString: SpannableString
    private var spannableStringSize = 0

    companion object {
        @JvmStatic
        fun newInstance() = CoordinatorFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val behavior = ButtonBehavior(requireContext())
        (binding.buttonChangeColor.layoutParams as CoordinatorLayout.LayoutParams).behavior = behavior

        /*binding.textViewCoordinator.typeface = Typeface.createFromAsset(
            requireActivity().assets,
            "fonts/Nautilus.otf"
        )*/

        spannableString = SpannableString(binding.textViewCoordinator.text)

        binding.textViewCoordinator.setText(spannableString, TextView.BufferType.SPANNABLE)
        spannableString = binding.textViewCoordinator.text as SpannableString
        spannableStringSize = spannableString.length

        binding.buttonChangeColor.setOnClickListener {
            changeColour()
        }
        changeColour()
    }

    private fun changeColour() {

        when (currentColor) {
            7 -> {
                setColour(R.color.rainbow_red)
                currentColor = 1
            }
            1 -> {
                setColour(R.color.rainbow_orange)
                currentColor = 2
            }
            2 -> {
                setColour(R.color.rainbow_yellow)
                currentColor = 3
            }
            3 -> {
                setColour(R.color.rainbow_green)
                currentColor = 4
            }
            4 -> {
                setColour(R.color.rainbow_light_blue)
                currentColor = 5
            }
            5 -> {
                setColour(R.color.rainbow_blue)
                currentColor = 6
            }
            6 -> {
                setColour(R.color.rainbow_purple)
                currentColor = 7
            }
        }
    }

    private fun setColour(color: Int) {
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), color)),
            0,
            spannableStringSize,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}