package com.example.smartcity.utils

import android.widget.Toast
import com.example.smartcity.SmartCity
import com.google.android.material.snackbar.BaseTransientBottomBar

object ToastUtil {

    fun toast(charSequence: CharSequence){
        Toast.makeText(SmartCity.application, charSequence, Toast.LENGTH_SHORT).show()
    }

    fun toast(charSequence: CharSequence, @BaseTransientBottomBar.Duration duration: Int){
        Toast.makeText(SmartCity.application, charSequence, duration).show()
    }

}