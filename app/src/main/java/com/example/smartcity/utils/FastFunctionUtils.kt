package com.example.smartcity.utils

import android.content.Context
import android.content.Intent
import com.example.smartcity.SmartCity

object FastFunctionUtils {
    fun startActivity(context: Context, activityClass: Class<*>){
        context.startActivity(Intent(context, activityClass))
    }
    fun dp2px(dp: Float): Int {
        val density = SmartCity.application.resources.displayMetrics.density
        return (dp * density + 0.5F).toInt()
    }
}