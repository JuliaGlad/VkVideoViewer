package myapplication.android.vkvideoviewer.presentation.video.recycler_view

import android.graphics.drawable.Drawable
import android.net.Uri
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
import myapplication.android.vkvideoviewer.databinding.RecyclerViewVideoItemBinding
import myapplication.android.vkvideoviewer.presentation.listener.ClickListener

class VideoItemAdapter
    : ListAdapter<VideoItemModel, RecyclerView.ViewHolder>(VideoItemCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewVideoItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    class ViewHolder(private val binding: RecyclerViewVideoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: VideoItemModel) {
            with(binding) {
                title.text = model.title
                viewsCount.text = "${model.views}"
                downloadsCount.text = "${model.downloads}"
                duration.text = "00:${model.duration}"
                binding.loader.root.visibility = VISIBLE
                Glide.with(itemView.context)
                    .load(model.thumbnail.toUri())
                    .listener(object : RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>,
                            isFirstResource: Boolean
                        ): Boolean {
                            Log.e("Loading in videoItem adapter failed", e?.message.toString())
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
                setClickListeners(model.actionClickListener, model.itemClickListener)
            }
        }

        private fun setClickListeners(actionListener: ClickListener, itemListener: ClickListener) {
            binding.downloadAction.setOnClickListener { actionListener.onClick() }
            binding.item.setOnClickListener { itemListener.onClick() }
        }
    }

}