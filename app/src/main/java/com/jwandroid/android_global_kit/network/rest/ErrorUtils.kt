package com.jwandroid.android_global_kit.network.rest

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.Response

class ErrorUtils {
    companion object {
        const val NOT_FOUND = "800"

        fun getError (response: Response<*>? = null): ErrorUtils {
            response?.errorBody()?.let {
                val responseErrorBody: String = it.string()
                responseErrorBody.let { errorBody->
                    return Gson().fromJson(errorBody, ErrorUtils::class.java)
                }
            }
            return ErrorUtils()
        }

        fun showToast (context: Context, errorUtils: ErrorUtils) {
            Toast.makeText(context, errorUtils.provideMessage(context), Toast.LENGTH_SHORT).show()
        }

    }

    @SerializedName("errorCode")
    var code: String? = null
    private var message: String = "Error!"

    fun provideMessage (context: Context, showMesFromServer: Boolean = false): String {
        if(showMesFromServer) return message
        return when(code) {
            NOT_FOUND -> "Error!"
            else ->"Error!"
        }
    }

    fun displayMessage (context: Context, showMesFromServer: Boolean = false) {
        Toast.makeText(context, provideMessage(context, showMesFromServer), Toast.LENGTH_SHORT).show()
    }

}