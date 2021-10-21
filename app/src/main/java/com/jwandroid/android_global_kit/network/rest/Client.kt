package com.jwandroid.android_global_kit.network.rest

import android.util.Log
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Client {

    companion object {
        var retrofit: Retrofit? = null

        @JvmStatic
        fun getApiService(): Store {
            if (retrofit == null) {
                val builder = OkHttpClient().newBuilder()
                builder.readTimeout(15, TimeUnit.SECONDS)
                builder.connectTimeout(15, TimeUnit.SECONDS)
                builder.writeTimeout(15, TimeUnit.SECONDS)
//                builder.retryOnConnectionFailure(false)
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                val interceptorForDebug = HttpLoggingInterceptor()
                if (ApiConfig.DEBUG) interceptorForDebug.setLevel(HttpLoggingInterceptor.Level.BODY) else {
                    interceptorForDebug.setLevel(HttpLoggingInterceptor.Level.NONE)
                }
                //builder.addInterceptor(interceptorForDebug)
                builder.addInterceptor(
                    LoggingInterceptor.Builder()
                        .setLevel(Level.BODY)
                        .log(Log.DEBUG)
                        .build()
                )
                retrofit = Retrofit.Builder()
                    .client(builder.build())
                    .baseUrl(ApiConfig.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit!!.create(Store::class.java)
        }

    }
}