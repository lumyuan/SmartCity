package com.example.smartcity.base

import android.app.UiModeManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.smartcity.R
import com.gyf.immersionbar.ImmersionBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseActivity: AppCompatActivity(), CoroutineScope by MainScope(){

    inline fun <VB: ViewBinding> AppCompatActivity.binding(
        crossinline inflater: (LayoutInflater) -> VB
    ) = lazy{
        inflater(layoutInflater).apply {
            setContentView(root)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uiModeManager = getSystemService(UI_MODE_SERVICE) as UiModeManager
        ImmersionBar.with(this)
            .transparentStatusBar()
            .statusBarDarkFont(uiModeManager.nightMode == UiModeManager.MODE_NIGHT_NO)
            .keyboardEnable(true)  //解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
            .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)  //单独指定软键盘模式
            .init()
        initView()
    }

    protected open fun initView(){}

    protected open fun <T : BaseViewModel> initViewModel(clazz: Class<T>): T{
        return ViewModelProvider(this)[clazz]
    }

    override fun onDestroy() {
        //销毁协程
        this.cancel()
        super.onDestroy()
    }
}