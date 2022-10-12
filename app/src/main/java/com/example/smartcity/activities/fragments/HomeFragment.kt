package com.example.smartcity.activities.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.smartcity.R
import com.example.smartcity.base.BaseFragment
import com.example.smartcity.databinding.FragmentHomeBinding
import com.gyf.immersionbar.ImmersionBar

class HomeFragment : BaseFragment() {

    private val binding by binding(FragmentHomeBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }

    override fun loadSingData(activity: AppCompatActivity) {
        super.loadSingData(activity)
        ImmersionBar.with(this)
            .setOnKeyboardListener { isPopup, keyboardHeight ->
                if (!isPopup) {

                }
            }
    }
}