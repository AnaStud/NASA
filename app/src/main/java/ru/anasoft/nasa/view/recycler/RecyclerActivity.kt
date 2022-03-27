package ru.anasoft.nasa.view.recycler

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.ItemTouchHelper
import ru.anasoft.nasa.R
import ru.anasoft.nasa.databinding.ActivityRecyclerBinding
import ru.anasoft.nasa.utils.themeActivityMain

class RecyclerActivity : AppCompatActivity() {

    lateinit var binding: ActivityRecyclerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (themeActivityMain) {
            1 -> { setTheme(R.style.MyBlueTheme) }
            2 -> { setTheme(R.style.MyGreenTheme) }
            3 -> { setTheme(R.style.MyRedTheme) }
            else -> {
                themeActivityMain = 1
                setTheme(R.style.MyBlueTheme)
            }
        }

        if (Build.VERSION.RELEASE.toInt() > 10) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() {

        val data = arrayListOf(
            Pair(Data(getString(R.string.event), getString(R.string.eventDescription), TYPE_EVENT), false),
            Pair(Data(getString(R.string.event), getString(R.string.eventDescription), TYPE_EVENT), false),
            Pair(Data(getString(R.string.event), getString(R.string.eventDescription), TYPE_EVENT), false),
            Pair(Data(getString(R.string.note), getString(R.string.noteDescription), TYPE_NOTE), false),
            Pair(Data(getString(R.string.note), getString(R.string.noteDescription), TYPE_NOTE), false),
            Pair(Data(getString(R.string.note), getString(R.string.noteDescription), TYPE_NOTE), false),
        )
        data.shuffle()
        data.add(0, Pair(Data(getString(R.string.header), type = TYPE_HEADER), false))

        val adapter = RecyclerActivityAdapter(object : OnClickItemListener {
            override fun onItemClick(data: Data) {
                Toast.makeText(baseContext, data.name, Toast.LENGTH_SHORT).show()
            }
        })
        adapter.setData(data)
        binding.recyclerView.adapter = adapter

        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView)
    }
}

fun interface OnClickItemListener {
    fun onItemClick(data: Data)
}