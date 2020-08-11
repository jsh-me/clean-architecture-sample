package com.jsh.tenqube.presentation.ui.first

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.jsh.tenqube.databinding.ActivityMainBinding
import com.jsh.tenqube.presentation.ui.second.SecondActivity
import com.jsh.tenqube.presentation.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FirstActivity : AppCompatActivity() {

    private val viewModel by viewModels<FirstViewModel>()
    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Timber.e("Activity Created")

        setupBinding()
        observeData()

    }


    private fun setupBinding(){
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    private fun observeData(){
        viewModel.shopAndLabelList.observe(this, Observer {
            binding.mainRecycler.adapter?.notifyDataSetChanged()
            Timber.e("activity invoke")
        })

        viewModel.addButtonClicked.observe(this, Observer {
            val intent = Intent(this@FirstActivity, SecondActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1000) {
            if (resultCode == 1000) {
                Toast.makeText(this, "Edit", Toast.LENGTH_LONG).show()
                binding.mainRecycler.adapter?.notifyDataSetChanged()
            } else if (resultCode == 1200) {
                Toast.makeText(this, "Delete", Toast.LENGTH_LONG).show()
                binding.mainRecycler.adapter?.notifyDataSetChanged()

            }
        }
    }

}