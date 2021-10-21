package com.jwandroid.android_global_kit.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jwandroid.android_global_kit.application.Constants
import com.jwandroid.android_global_kit.network.entity.ImageEntity
import com.jwandroid.android_global_kit.network.entity.TestEntity
import com.jwandroid.android_global_kit.network.rest.Client
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PixelViewModel : ViewModel()  {

    private val pixelLiveData: MutableLiveData<ArrayList<ImageEntity>> by lazy {
        MutableLiveData<ArrayList<ImageEntity>>().also {
            pixelImageRequest()
        }
    }

    fun getPixel(): LiveData<ArrayList<ImageEntity>> {
        return pixelLiveData
    }

    private fun pixelImageRequest() {
        Client.getApiService()
            .getPixel("563492ad6f91700001000001748e7af33c444e89b6f60cefbfcc0ce3").enqueue(object :
                Callback<TestEntity> {
                override fun onFailure(call: Call<TestEntity>, t: Throwable) {}
                override fun onResponse(call: Call<TestEntity>, response: Response<TestEntity>) {
                    if (response.code() == Constants.HTTP_CODE_OK) {
                        response.body()?.photos.let {
                            pixelLiveData.value = it
                        }
                    }
                }
            })
    }

}