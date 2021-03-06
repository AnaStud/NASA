package ru.anasoft.nasa.view.main

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BlurMaskFilter
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.*
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.transition.*
import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import ru.anasoft.nasa.R
import ru.anasoft.nasa.databinding.FragmentMainBinding
import ru.anasoft.nasa.utils.URL_WIKI
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

    private var isExpanded = false

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

        binding.imageView.setOnClickListener {
            isExpanded = !isExpanded
            TransitionManager.beginDelayedTransition(
                binding.main, TransitionSet()
                    .addTransition(ChangeBounds())
                    .addTransition(ChangeImageTransform())
            )
            val params: ViewGroup.LayoutParams = binding.imageView.layoutParams
            params.height =
                if (isExpanded) ViewGroup.LayoutParams.MATCH_PARENT
                else ViewGroup.LayoutParams.WRAP_CONTENT
            binding.imageView.layoutParams = params
            binding.imageView.scaleType =
                if (isExpanded) ImageView.ScaleType.CENTER_CROP
                else ImageView.ScaleType.FIT_CENTER
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
                binding.imageViewLoading.isVisible = true
                val fade = Fade()
                fade.duration= 1000L
                TransitionManager.beginDelayedTransition(binding.main, fade)
                binding.imageView.isVisible = false
            }
            is PictureOfTheDayState.Success -> {
                with(pictureOfTheDayState.serverResponseData) {
                    binding.imageViewLoading.isVisible = false
                    val fade = Fade()
                    fade.duration= 2000L
                    TransitionManager.beginDelayedTransition(binding.main, fade)
                    binding.imageView.isVisible = true

                    binding.also {
                        it.imageView.load(hdurl)
                        it.included.bottomSheetDescriptionHeader.text = title
                        it.included.bottomSheetDescription.text = explanation
                        it.included.bottomSheetDescription.typeface = Typeface.createFromAsset(
                            requireActivity().assets,
                            "fonts/Nautilus.otf"
                        )

                        addSpansToText()
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

    @SuppressLint("ResourceAsColor")
    private fun addSpansToText() {

        var spannableString = SpannableString(binding.included.bottomSheetDescription.text)
        binding.included.bottomSheetDescription.setText(spannableString, TextView.BufferType.SPANNABLE)
        spannableString = binding.included.bottomSheetDescription.text as SpannableString
        val spannableStringSize = spannableString.length
        val partOfText = spannableStringSize/10

        spannableString.setSpan(
            BackgroundColorSpan(ContextCompat.getColor(requireContext(), R.color.red_700)),
            0,
            partOfText,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            ScaleXSpan(1.5f),
            partOfText,
            2 * partOfText,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            RelativeSizeSpan(0.7f),
            2 * partOfText,
            3 * partOfText,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            UnderlineSpan(),
            3 * partOfText,
            4 * partOfText,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            4 * partOfText,
            5 * partOfText,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            StrikethroughSpan(),
            5 * partOfText,
            6 * partOfText,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            MaskFilterSpan(BlurMaskFilter(5f, BlurMaskFilter.Blur.SOLID)),
            6 * partOfText,
            7 * partOfText,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            SubscriptSpan(),
            7 * partOfText,
            8 * partOfText,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.green_500)),
            8 * partOfText,
            spannableStringSize,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            spannableString.setSpan(
                LineHeightSpan.Standard(70),
                0,
                spannableStringSize,
                SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
            )
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
                    .replace(R.id.container, SettingsFragment.newInstance())
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
