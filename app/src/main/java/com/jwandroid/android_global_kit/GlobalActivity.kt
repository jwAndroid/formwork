package com.jwandroid.android_global_kit

import com.jwandroid.android_global_kit.application.rx.RxBus
import com.jwandroid.android_global_kit.base.UtilityBase
import com.jwandroid.android_global_kit.databinding.ActivityGlobalBinding
import com.jwandroid.android_global_kit.network.entity.RxTestEntity
import com.jwandroid.android_global_kit.utils.LogUtils
import com.jwandroid.android_global_kit.view.PixelActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class GlobalActivity : UtilityBase.BaseAppCompatActivity<ActivityGlobalBinding>(R.layout.activity_global) {
    override fun ActivityGlobalBinding.onCreate() {
        GlobalScope.launch {
            delay(1000L)
            PixelActivity.run(this@GlobalActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        RxBus.subscribe(RxBus.EVENT, this) {
            if (it is RxTestEntity) {
                LogUtils.logD("JwAndroidRx", "in GlobalActivity result: $it")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.unregister(this)
    }
}