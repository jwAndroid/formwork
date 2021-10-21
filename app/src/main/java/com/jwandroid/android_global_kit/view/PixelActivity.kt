package com.jwandroid.android_global_kit.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.jwandroid.android_global_kit.R
import com.jwandroid.android_global_kit.application.rx.RxBus
import com.jwandroid.android_global_kit.base.UtilityBase
import com.jwandroid.android_global_kit.databinding.ActivityPixelBinding
import com.jwandroid.android_global_kit.network.entity.ImageEntity
import com.jwandroid.android_global_kit.network.entity.RxTestEntity
import com.jwandroid.android_global_kit.utils.LogUtils

class PixelActivity: UtilityBase.BaseAppCompatActivity<ActivityPixelBinding>(R.layout.activity_pixel) {

    companion object {
        fun run (context: Context) {
            val intent = Intent(context, PixelActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
    }

    override fun ActivityPixelBinding.onCreate() {
        val pixelViewModel : PixelViewModel by viewModels()

        pixelViewModel.getPixel().observe(this@PixelActivity, { imageList ->
            applyData(imageList)
        })

        binding.onPublish.setOnClickListener {
            RxBus.publish(RxBus.EVENT , RxTestEntity("지웅" , 27 , true))
        }

    }

    private fun applyData(images : ArrayList<ImageEntity>){
        images.forEach { image ->
            LogUtils.logD("initData","response: ${image.src?.original}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.unregister(this)
    }

}