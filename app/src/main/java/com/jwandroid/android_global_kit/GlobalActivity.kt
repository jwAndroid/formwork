package com.jwandroid.android_global_kit

import com.jwandroid.android_global_kit.base.UtilityBase
import com.jwandroid.android_global_kit.databinding.ActivityGlobalBinding
import com.jwandroid.android_global_kit.view.PixelActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GlobalActivity : UtilityBase.BaseAppCompatActivity<ActivityGlobalBinding>(R.layout.activity_global) {
    override fun ActivityGlobalBinding.onCreate() {

        GlobalScope.launch {
            delay(2000L)
            PixelActivity.run(this@GlobalActivity)
        }
    }
}