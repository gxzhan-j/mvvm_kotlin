package com.example.mvvm_kotlin.pages.post

import android.view.View
import android.view.View.GONE
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_kotlin.R
import com.example.mvvm_kotlin.base.BaseFragment
import com.example.mvvm_kotlin.common.observe
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.layout_post.*


class PostFragment : BaseFragment<ViewModel, PostViewModel>(), PostClickListener {

    private lateinit var imageAdapter: PostAdapter

    /*--------------------------------------------------------------------------------------------*/
    /* Initialization */
    override fun getLayoutRes(): Int = R.layout.layout_post

    override fun onCreateViewModel(): PostViewModel? {
        return ViewModelProvider(this).get(PostViewModel::class.java)
    }

    override fun initViews(layout: View) {
        super.initViews(layout)

        postRecyclerView.also {
            it.layoutManager = LinearLayoutManager(context)
            imageAdapter = PostAdapter(
                    arrayListOf(),
                    this)
            it.adapter = imageAdapter
        }
    }

    override fun onViewModelCreated() {
        super.onViewModelCreated()

        //observe if the response is empty
        observe(viewModel.emptyResponse) { isEmpty ->
            if (isEmpty) postRecyclerView.visibility = GONE
        }

        //observe the imageInfo data change
        observe(viewModel.postList) {
            imageAdapter.updateImageList(it)
        }

        //observe the filter string
        observe(viewModel.imageFilters) {
            showSnackBar(it)
        }

        viewModel.start()
    }

    /*--------------------------------------------------------------------------------------------*/
    /* Internal Helper */
    private fun showSnackBar(filter: String?) {
        val filter = if (filter.isNullOrEmpty()) requireContext().getString(R.string.no_filter_error) else filter
        view?.let {
            val snack = Snackbar.make(it, filter, Snackbar.LENGTH_LONG)
            snack.show()
        }
    }

    /*--------------------------------------------------------------------------------------------*/
    /* PostClickListener */
    override fun onPostClicked(position: Int) {
        viewModel.showFilter(position)
    }


}