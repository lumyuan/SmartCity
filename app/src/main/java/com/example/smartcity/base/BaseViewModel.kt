package com.example.smartcity.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartcity.bean.LoadState

/**
 * ViewModel 基类
 * @author ssq
 */
abstract class BaseViewModel : ViewModel() {
    // 加载状态
    open val loadState = MutableLiveData<LoadState>()
}