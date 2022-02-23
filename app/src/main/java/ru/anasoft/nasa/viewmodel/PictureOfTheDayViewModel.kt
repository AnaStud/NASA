package ru.anasoft.nasa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.anasoft.nasa.BuildConfig
import ru.anasoft.nasa.repository.PictureOfTheDayResponseData
import ru.anasoft.nasa.repository.PictureOfTheDayRetrofitImpl

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<PictureOfTheDayState> = MutableLiveData(),
    private val pictureOfTheDayRetrofitImpl: PictureOfTheDayRetrofitImpl = PictureOfTheDayRetrofitImpl()
): ViewModel() {

    fun getLiveData(): LiveData<PictureOfTheDayState> {
        return  liveData
    }

    fun sendServerRequest(){
        liveData.postValue(PictureOfTheDayState.Loading(50))
        pictureOfTheDayRetrofitImpl
            .getRetrofitImpl()
            .getPictureOfTheDay(BuildConfig.NASA_API_KEY)
            .enqueue(object : Callback<PictureOfTheDayResponseData> {
                override fun onResponse(call: Call<PictureOfTheDayResponseData>,
                                        response: Response<PictureOfTheDayResponseData>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            liveData.postValue(PictureOfTheDayState.Success(it))
                        }
                    }
                    else{
                        liveData.value = PictureOfTheDayState.Error(Throwable(response.message()))
                    }
                }

                override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
                    PictureOfTheDayState.Error(t)
                }
            })
    }
}