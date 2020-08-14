package com.example.mvvm_kotlin.pages.post

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.mvvm_kotlin.R
import com.example.mvvm_kotlin.databinding.ItemPostBinding
import com.example.mvvm_kotlin.models.responses.Post

class PostAdapter(
        private val imageList: ArrayList<Post>,
        private val listener: PostClickListener
) : RecyclerView.Adapter<PostAdapter.PostsViewHolder>() {

    /*--------------------------------------------------------------------------------------------*/
    /* Public function */
    fun updateImageList(newImageList: List<Post>) {
        imageList.clear()
        imageList.addAll(newImageList)
        notifyDataSetChanged()
    }

    /*--------------------------------------------------------------------------------------------*/
    /* Initialization */
    override fun getItemCount() = imageList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            PostsViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.item_post,
                            parent,
                            false
                    )
            )

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val post = imageList[position]
        holder.recyclerViewPostBinding.also {
            it.post = post
            it.postCardView.setOnClickListener {
                listener.onPostClicked(position)
            }
        }
    }

    /*--------------------------------------------------------------------------------------------*/
    /* View Holder */
    inner class PostsViewHolder(
            val recyclerViewPostBinding: ItemPostBinding
    ) : RecyclerView.ViewHolder(recyclerViewPostBinding.root)

}

/*--------------------------------------------------------------------------------------------*/
/* DataBinding Helper */
@BindingAdapter("postImage")
fun loadImage(view: ImageView, url: String) {
    val option = RequestOptions()
            .error(R.drawable.default_image)
    val imageUrl = view.context.getString(R.string.api_post_base_url) + url
    Glide.with(view)
            .setDefaultRequestOptions(option)
            .load(imageUrl)
            .into(view)
}