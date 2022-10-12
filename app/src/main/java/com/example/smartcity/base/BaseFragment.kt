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
        val uiModeManager =
            requireActivity().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        ImmersionBar.with(this)
            .transparentStatusBar()  //透明状态栏，不写默认透明色
            .statusBarDarkFont(uiModeManager.nightMode == UiModeManager.MODE_NIGHT_NO)
            .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
            .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)  //单独指定软键盘模式
            .init()
    }

    override fun onPause() {
        super.onPause()
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