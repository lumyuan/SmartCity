package com.example.smartcity.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.smartcity.R
import com.example.smartcity.databinding.DialogSearchServiceBinding
import com.example.smartcity.net.pojo.ServiceBean
import com.example.smartcity.ui.adapters.DialogServiceAdapter

class SearchServiceDialog(private val list: ArrayList<ServiceBean>, context: Context): Dialog(context, R.style.DialogTheme) {

    private lateinit var binding: DialogSearchServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogSearchServiceBinding.bind(
            View.inflate(context, R.layout.dialog_search_service, null)
        )
        setContentView(binding.root)

        val layoutParams = window?.attributes
        layoutParams?.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.CENTER
        }
        window?.attributes = layoutParams
        if (list.size > 0){
            binding.none.visibility = View.GONE
        }else {
            binding.none.visibility = View.VISIBLE
        }

        //渲染列表
        binding.serviceList.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            adapter = DialogServiceAdapter(list)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {

            }
            else -> {}
        }
        return super.onKeyDown(keyCode, event)
    }

}