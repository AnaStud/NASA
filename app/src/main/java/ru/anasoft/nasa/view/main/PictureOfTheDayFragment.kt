package ru.anasoft.nasa.view.main

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import ru.anasoft.nasa.R
import ru.anasoft.nasa.databinding.FragmentMainBinding
import ru.anasoft.nasa.utils.URL_WIKI
import ru.anasoft.nasa.utils.themeActivityMain
import ru.anasoft.nasa.view.BaseFragment
import ru.anasoft.nasa.view.MainActivity
import ru.anasoft.nasa.view.settings.SettingsFragment
import ru.anasoft.nasa.viewmodel.PictureOfTheDayState
import ru.anasoft.nasa.viewmodel.PictureOfTheDayViewModel
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    companion object {
        @JvmStatic
        fun newInstance() = PictureOfTheDayFragment()
    }

    private var isMain: Boolean = true

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProvider(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (requireActivity() as MainActivity).setSupportActionBar(binding.bottomAppBar)

        initBottomSheetBehavior()
        initData()
        initButtons()

        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.twoDaysAgo -> { viewModel.sendServerRequest(takeDate(-2)) }
                R.id.yesterday -> { viewModel.sendServerRequest(takeDate(-1)) }
                else -> { viewModel.sendServerRequest() }
            }
        }
    }

    private fun takeDate(count: Int): String {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.DATE, count)
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        simpleDateFormat.timeZone = TimeZone.getTimeZone("EST")
        return simpleDateFormat.format(currentDate.time)
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private fun initBottomSheetBehavior() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.included.bottomSheetContainer)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun initData(){
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.sendServerRequest()
    }

    private fun renderData(pictureOfTheDayState: PictureOfTheDayState) {
        when (pictureOfTheDayState) {
            is PictureOfTheDayState.Loading -> {
                Toast.makeText(requireContext(), "Loading...", Toast.LENGTH_SHORT).show()
            }
            is PictureOfTheDayState.Success -> {
                with(pictureOfTheDayState.serverResponseData) {
                    binding.also {
                        it.imageView.load(hdurl)
                        it.included.bottomSheetDescriptionHeader.text = title
                        it.included.bottomSheetDescription.text = explanation
                    }
                }
            }
            is PictureOfTheDayState.Error -> {
                Snackbar.make(binding.main, "Error of loading", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Try again") { viewModel.sendServerRequest(takeDate(-1)) }
                    .show()
            }
        }
    }

    private fun initButtons() {
        with(binding) {
            inputLayout.setEndIconOnClickListener { _ ->
                startActivity(Intent(Intent.ACTION_VIEW).also {
                    it.data = Uri.parse("$URL_WIKI${inputEditText.text.toString()}")
                })
            }
            fab.setOnClickListener {
                if (isMain) {
                    changeBottomAppBar(
                        R.drawable.ic_back_fab,
                        null,
                        BottomAppBar.FAB_ALIGNMENT_MODE_END,
                        R.menu.menu_bottom_bar_other_screen
                    )
                }
                else {
                    changeBottomAppBar(
                        R.drawable.ic_plus_fab,
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_hamburger_menu_bottom_bar),
                        BottomAppBar.FAB_ALIGNMENT_MODE_CENTER,
                        R.menu.menu_bottom_bar
                    )
                }
                isMain = !isMain
            }
        }
    }

    private fun changeBottomAppBar(icFab: Int, icNav: Drawable?, fabAlignmentMode: Int, menuBottom: Int) {
        with(binding) {
            fab.setImageDrawable(ContextCompat.getDrawable(requireContext(), icFab))
            bottomAppBar.also {
                it.navigationIcon = icNav
                it.fabAlignmentMode = fabAlignmentMode
                it.replaceMenu(menuBottom)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_add_to_favotites ->
                Toast.makeText(requireContext(), "Added to favorites", Toast.LENGTH_SHORT).show()
            R.id.menu_item_settings -> {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, SettingsFragment.newInstance(themeActivityMain))
                    .addToBackStack("")
                    .commit()
            }
            R.id.menu_item_search ->
                Toast.makeText(requireContext(), "Find picture", Toast.LENGTH_SHORT).show()
            android.R.id.home -> {
                BottomNavigationDrawerFragment().show(requireActivity().supportFragmentManager, "")
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
