package com.example.smartcity.base

import android.app.UiModeManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar

abstract class BaseFragment: Fragment() {

    inline fun <VB : ViewBinding> Fragment.binding(
        crossinline inflater: (LayoutInflater) -> VB
    ) = lazy {
        inflater(layoutInflater).apply {

        }
    }

    private var isFirstShow = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        val appCompatActivity = requireActivity() as AppCompatActivity
        if (isFirstShow){
            loadSingData(appCompatActivity)
            isFirstShow = false
        }
        loadData(appCompatActivity)
    }

    //懒加载回调函数
    protected open fun loadData(activity: AppCompatActivity){}
    protected open fun loadSingData(activity: AppCompatActivity){}

}