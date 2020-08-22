package com.jsh.practice.presentation.util

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

inline fun <T: ViewBinding> AppCompatActivity.viewBinding(
        crossinline inflater: (LayoutInflater) -> T
) = lazy(LazyThreadSafetyMode.NONE) {
    inflater.invoke(layoutInflater)
}