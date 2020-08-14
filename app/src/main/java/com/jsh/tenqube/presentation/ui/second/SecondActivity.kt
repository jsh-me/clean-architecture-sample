package com.jsh.tenqube.presentation.ui.second

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.jsh.tenqube.R
import com.jsh.tenqube.databinding.ActivitySubBinding
import com.jsh.tenqube.presentation.ui.first.FirstActivity
import com.jsh.tenqube.presentation.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondActivity : AppCompatActivity() {

    private val viewModel by viewModels<SecondViewModel>()
    private val binding by viewBinding(ActivitySubBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.subViewModel = viewModel
        binding.lifecycleOwner = this

        initView()
        observeData()
    }

    private fun initView(){
        with(viewModel){
            setShopInfo(intent.getStringArrayListExtra("shopInfo")?: arrayListOf("","","",""))
        }
    }

    private fun observeData() {
        viewModel.deleteButtonClicked.observe(this, Observer {
            if(it == true) {
                Toast.makeText(this, "Delete Complete", Toast.LENGTH_SHORT).show()
                backToMain()
            } else {
                Toast.makeText(this, "Delete Error", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.editButtonClicked.observe(this, Observer {
            if(it == true){
                Toast.makeText(this, "Edit Complete", Toast.LENGTH_SHORT).show()
                backToMain()
            } else {
                Toast.makeText(this, "Edit Error", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.addButtonClicked.observe(this, Observer {
            if(it == true) {
                Toast.makeText(this, "Add Complete", Toast.LENGTH_SHORT).show()
                backToMain()
            } else {
                Toast.makeText(this, "Add Error", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.addImageButtonClicked.observe(this, Observer {
            viewModel.setImageUrl(resources.getString(R.string.testImage))
        })
    }

    private fun backToMain(){
        val intent = Intent(this, FirstActivity::class.java)
        startActivity(intent)
        finish()
    }
}