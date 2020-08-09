package com.jsh.tenqube.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jsh.tenqube.databinding.ActivityMainBinding
import com.jsh.tenqube.presentation.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()

        Timber.e("Activity Created")
        observeData()
    }

    private fun observeData(){
        viewModel.shopList.observe(this, Observer {
            binding.mainRecycler.adapter?.notifyDataSetChanged()
            Timber.e("activity invoke")
        })
    }
}