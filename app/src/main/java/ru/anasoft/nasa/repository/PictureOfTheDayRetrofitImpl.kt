package ru.anasoft.nasa.repository

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.anasoft.nasa.utils.BASE_URL_NASA

class PictureOfTheDayRetrofitImpl {
    fun getRetrofitImpl():PictureOfTheDayAPI{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_NASA)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
        return retrofit.create(PictureOfTheDayAPI::class.java)
    }
}