package com.example.smartcity.model

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

object MainViewInternetModel: ViewModel() {
    private val viewMap = MutableLiveData(HashMap<String, View>())
    fun getView(key: String): View? {
        return viewMap.value?.get(key)
    }
    fun putView(key: String, view: View){
        viewMap.value?.set(key, view)
    }
}