package ru.anasoft.nasa.view.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.anasoft.nasa.R
import ru.anasoft.nasa.databinding.BottomNavigationLayoutBinding
import ru.anasoft.nasa.view.layouts.LayoutActivity
import ru.anasoft.nasa.view.navigation.BottomNavigationActivity
import ru.anasoft.nasa.view.recycler.RecyclerActivity

class BottomNavigationDrawerFragment : BottomSheetDialogFragment() {
    private var _binding: BottomNavigationLayoutBinding? = null
    private val binding: BottomNavigationLayoutBinding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = BottomNavigationLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_one->{
                    startActivity(Intent(requireContext(), BottomNavigationActivity::class.java))
                    dismiss()
                }
                R.id.navigation_homework4->{
                    startActivity(Intent(requireContext(), LayoutActivity::class.java))
                    dismiss()
                }
                R.id.navigation_homework6->{
                    startActivity(Intent(requireContext(), RecyclerActivity::class.java))
                    dismiss()
                }
             }
            true
        }
    }
}