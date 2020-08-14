package com.example.mvvm_kotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.mvvm_kotlin.common.observe


abstract class BaseFragment<SV : ViewModel, V : BaseViewModel> : Fragment() {

    private var _viewModel: V? = null
    protected val viewModel: V
        get() = _viewModel!!

    /*--------------------------------------------------------------------------------------------*/
    /* Abstract */
    @LayoutRes
    protected abstract fun getLayoutRes(): Int

    /*--------------------------------------------------------------------------------------------*/
    /* Fragment */
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setListeners(view)

        _viewModel = onCreateViewModel()
        onViewModelCreated()
    }

    /*--------------------------------------------------------------------------------------------*/
    /* Initialization */
    protected open fun initViews(layout: View) = Unit

    protected open fun setListeners(layout: View) = Unit

    /*--------------------------------------------------------------------------------------------*/
    /* ViewModel */
    protected abstract fun onCreateViewModel(): V?

    protected open fun onViewModelCreated() {
        observe(viewModel.showErrorMessageEvent) {
            showErrorMessage(it)
        }
    }

    protected open fun showErrorMessage(message: String, payload: Any? = null) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
                .show()
    }

}