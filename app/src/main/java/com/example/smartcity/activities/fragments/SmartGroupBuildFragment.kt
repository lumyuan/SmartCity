package com.example.smartcity.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.smartcity.R
import com.example.smartcity.base.BaseFragment
import com.example.smartcity.databinding.FragmentSmartGroupBuildBinding

class SmartGroupBuildFragment : BaseFragment() {

    private val binding by binding(FragmentSmartGroupBuildBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SmartGroupBuildFragment()
    }

    override fun loadSingData(activity: AppCompatActivity) {
        super.loadSingData(activity)

    }
}