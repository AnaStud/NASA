package ru.anasoft.nasa.view.recycler

const val TYPE_EVENT = 1
const val TYPE_NOTE = 2
const val TYPE_HEADER = 3

data class Data(val name:String = "Название",
                val description:String="",
                val type:Int = TYPE_EVENT)