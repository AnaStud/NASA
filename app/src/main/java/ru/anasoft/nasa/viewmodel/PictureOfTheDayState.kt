package ru.anasoft.nasa.viewmodel

import ru.anasoft.nasa.repository.PictureOfTheDayResponseData

sealed class PictureOfTheDayState {
    data class Loading(val progress: Int?) : PictureOfTheDayState()
    data class Success(val serverResponseData: PictureOfTheDayResponseData) : PictureOfTheDayState()
    data class Error(val error: Throwable) : PictureOfTheDayState()
}