package ru.anasoft.nasa.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

open class BaseFragment <VBinding : ViewBinding>(
    private val inflateBinding : (inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean) -> VBinding
) : Fragment() {

    private var _binding: VBinding? = null
    val binding: VBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflateBinding.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}