package com.jwandroid.android_global_kit.utils

import android.util.Log

class LogUtils {

    companion object{
        fun logE(tag: String?, message: String?) {
            Log.e(tag, message!!)
        }

        fun logD(tag: String?, message: String?) {
            Log.d(tag, message!!)
        }

        fun logW(tag: String?, message: String?) {
            Log.w(tag, message!!)
        }

        fun logFirebase(tag: String?, message: String?) {
            Log.w(tag, message!!)
        }

        fun debugIssue(issueNumber: Int, message: String?) {
            Log.e("Issue$issueNumber", message!!)
        }
    }

}