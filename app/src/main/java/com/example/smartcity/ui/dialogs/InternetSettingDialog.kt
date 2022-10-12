package com.example.smartcity.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.example.smartcity.R
import com.example.smartcity.databinding.DialogInternetSettingBinding
import com.example.smartcity.model.SmartCitySettingsModel
import com.example.smartcity.utils.ToastUtil

class InternetSettingDialog(context: Context): Dialog(context, R.style.DialogTheme) {

    private lateinit var binding: DialogInternetSettingBinding
    private val smartCitySettingsBean = SmartCitySettingsModel.smartCitySettings.value!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogInternetSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val window = this.window
        val attributes = window!!.attributes
        attributes.apply {
            this.gravity = Gravity.CENTER
            this.width = WindowManager.LayoutParams.MATCH_PARENT
            this.height = WindowManager.LayoutParams.WRAP_CONTENT
        }
        window.attributes = attributes

        initView()
    }

    private fun initView() {
        binding.dnsEditor.setText(smartCitySettingsBean.dns)
        binding.portEditor.setText(smartCitySettingsBean.port)

        //保存
        binding.define.setOnClickListener {
            val dns = binding.dnsEditor.text.toString().trim()
            val port = binding.portEditor.text.toString().trim()
            if (dns != "" && dns != "null" && port != "" && port != "null"){
                smartCitySettingsBean.dns = dns
                smartCitySettingsBean.port = port
                SmartCitySettingsModel.smartCitySettings.value = smartCitySettingsBean
                dismiss()
            }else {
                ToastUtil.toast("请输入正确的网络配置")
            }
        }

        //关闭
        binding.cancel.setOnClickListener {
            dismiss()
        }
    }

}