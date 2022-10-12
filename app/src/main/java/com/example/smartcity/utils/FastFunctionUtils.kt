package com.example.smartcity.utils

import android.content.Context
import android.content.Intent

object FastFunctionUtils {
    fun startActivity(context: Context, activityClass: Class<*>){
        context.startActivity(Intent(context, activityClass))
    }
}