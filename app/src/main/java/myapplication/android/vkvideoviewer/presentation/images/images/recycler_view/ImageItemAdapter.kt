package myapplication.android.vkvideoviewer.presentation.images.images.recycler_view

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import jp.wasabeef.glide.transformations.BlurTransformation
import myapplication.android.vkvideoviewer.databinding.RecyclerViewImageItemBinding
import myapplication.android.vkvideoviewer.databinding.RecyclerViewVideoItemBinding
import myapplication.android.vkvideoviewer.presentation.listener.ClickListener

class ImageItemAdapter
    : ListAdapter<ImageItemModel, RecyclerView.ViewHolder>(ImageItemCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewImageItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    class ViewHolder(private val binding: RecyclerViewImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: ImageItemModel) {
            with(binding) {
                title.text = model.title
                viewsCount.text = "${model.views}"
                downloadsCount.text = "${model.downloads}"
                binding.loader.root.visibility = VISIBLE

                setImageUri(model)
                setClickListeners(model.itemClickListener)
            }
        }

        private fun RecyclerViewImageItemBinding.setImageUri(
            model: ImageItemModel
        ) {
            Glide.with(itemView.context)
                .load(model.thumbnail.toUri())
                .transform(BlurTransformation(25, 3))
                .into(thumbnailBlurry)
            thumbnailBlurry.alpha = 0.7f

            Glide.with(itemView.context)
                .load(model.thumbnail.toUri())
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.e("Loading in imageItem adapter failed", e?.message.toString())
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.loader.root.visibility = GONE
                        return false
                    }

                })
                .into(thumbnail)
        }

        private fun setClickListeners(itemListener: ClickListener) {
            binding.item.setOnClickListener { itemListener.onClick() }
        }
    }

}