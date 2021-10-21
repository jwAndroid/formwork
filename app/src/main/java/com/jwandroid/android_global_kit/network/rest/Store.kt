package com.jwandroid.android_global_kit.network.rest

import com.jwandroid.android_global_kit.network.entity.TestEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface Store {

    @GET("search?query=nature&orientation=portrait&size=small&per_page=20")
    fun getPixel(@Header("Authorization") key: String): Call<TestEntity>
}