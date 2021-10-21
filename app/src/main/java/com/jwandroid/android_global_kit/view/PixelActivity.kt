package com.jwandroid.android_global_kit.view

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jwandroid.android_global_kit.R
import com.jwandroid.android_global_kit.application.Constants
import com.jwandroid.android_global_kit.base.UtilityBase
import com.jwandroid.android_global_kit.databinding.ActivityPixelBinding
import com.jwandroid.android_global_kit.network.entity.ImageEntity
import com.jwandroid.android_global_kit.network.entity.TestEntity
import com.jwandroid.android_global_kit.network.rest.Client
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class PixelActivity: UtilityBase.BaseAppCompatActivity<ActivityPixelBinding>(R.layout.activity_pixel) {

    companion object {
        fun run (context: Context) {
            val intent = Intent(context, PixelActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
    }

    override fun ActivityPixelBinding.onCreate() {
        val pixelViewModel : PixelViewModel by viewModels()
        pixelViewModel.getPixel().observe(this@PixelActivity, { image ->
            initData(image)
        })
    }

    private fun initData(images : ArrayList<ImageEntity>){
        images.forEach { image ->
            Timber.d("response: " + image.src?.original)
        }
    }

}