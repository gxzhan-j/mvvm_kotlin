package com.example.mvvm_kotlin.pages.splash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.mvvm_kotlin.R
import com.example.mvvm_kotlin.base.BaseFragment
import com.example.mvvm_kotlin.base.BaseViewModel
import kotlinx.android.synthetic.main.layout_splash.*

class SplashFragment : BaseFragment<ViewModel, BaseViewModel>() {

    override fun getLayoutRes(): Int = R.layout.layout_splash

    override fun onCreateViewModel(): BaseViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        go_to_post_button.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_postFragment)
        }
    }

}