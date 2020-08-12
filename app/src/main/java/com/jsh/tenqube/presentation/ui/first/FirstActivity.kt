package com.jsh.tenqube.presentation.ui.first

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jsh.tenqube.databinding.ActivityMainBinding
import com.jsh.tenqube.presentation.ui.second.SecondActivity
import com.jsh.tenqube.presentation.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FirstActivity : AppCompatActivity() {

    private val viewModel by viewModels<FirstViewModel>()
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private lateinit var listAdapter : MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Timber.e("Activity Created")

        setupBinding()
        observeData()
        setupListAdapter()
    }

    private fun setupBinding(){
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
    }

    private fun observeData(){
        viewModel.shopAndLabelList.observe(this, Observer {
           // setupListAdapter()
            binding.mainRecycler.adapter?.notifyDataSetChanged()
            Timber.e("activity invoke")
        })

        viewModel.openShopListClicked.observe(this, Observer {
            openShopDetails(it)
        })

        viewModel.addButtonClicked.observe(this, Observer {
            val intent = Intent(this@FirstActivity, SecondActivity::class.java)
            startActivity(intent)
        })
    }

    private fun openShopDetails(shopInfo: ArrayList<String>){
        val intent = Intent(this@FirstActivity, SecondActivity::class.java)
        intent.putExtra("shopInfo", shopInfo)
        startActivity(intent)
    }

    private fun setupListAdapter(){
        val viewModel = binding.viewmodel
        if(viewModel != null){
            listAdapter = MainAdapter(viewModel)
            binding.mainRecycler.layoutManager = LinearLayoutManager(this)
            binding.mainRecycler.adapter = listAdapter
            Timber.e("complete")
        } else {
            Timber.e("ViewModel not initialized when attempting to set up adapter.")
        }
    }

}