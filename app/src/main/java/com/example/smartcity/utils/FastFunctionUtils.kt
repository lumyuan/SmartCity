package com.example.smartcity.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat
import com.example.smartcity.SmartCity
import com.example.smartcity.base.BaseActivity

object FastFunctionUtils {
    const val WRITE_EXTERNAL_STORAGE_PERMISSION = 101

    fun startActivity(context: Context, activityClass: Class<*>) {
        context.startActivity(Intent(context, activityClass))
    }

    fun dp2px(dp: Float): Int {
        val density = SmartCity.application.resources.displayMetrics.density
        return (dp * density + 0.5F).toInt()
    }

    fun getWritePermission(activity: BaseActivity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            WRITE_EXTERNAL_STORAGE_PERMISSION
        )
    }
}