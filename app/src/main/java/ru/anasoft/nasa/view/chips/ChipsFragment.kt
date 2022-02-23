package ru.anasoft.nasa.view.chips

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.chip.Chip
import ru.anasoft.nasa.databinding.FragmentChipsBinding
import ru.anasoft.nasa.view.BaseFragment

class ChipsFragment : BaseFragment<FragmentChipsBinding>(FragmentChipsBinding::inflate) {

    companion object {
        @JvmStatic
        fun newInstance() = ChipsFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.chipGroup) {
            setOnCheckedChangeListener { _, checkedId ->
                findViewById<Chip>(checkedId)?.let {
                    Toast.makeText(requireContext(), "$checkedId. ${it.text}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}