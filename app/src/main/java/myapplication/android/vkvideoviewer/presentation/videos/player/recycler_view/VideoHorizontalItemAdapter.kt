package myapplication.android.vkvideoviewer.presentation.videos.player.recycler_view

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
import myapplication.android.vkvideoviewer.databinding.RecyclerViewVideoHorizontalItemBinding
import myapplication.android.vkvideoviewer.presentation.listener.ClickListener

class VideoHorizontalItemAdapter
    : ListAdapter<VideoHorizontalItemModel, RecyclerView.ViewHolder>(VideoHorizontalItemCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            RecyclerViewVideoHorizontalItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    class ViewHolder(private val binding: RecyclerViewVideoHorizontalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: VideoHorizontalItemModel) {
            with(binding) {
                title.text = model.title
                viewsCount.text = "${model.views}"
                downloadsCount.text = "${model.downloads}"
                duration.text = "00:${model.duration}"
                binding.loader.visibility = VISIBLE
                Glide.with(itemView.context)
                    .load(model.thumbnail.toUri())
                    .listener(object : RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>,
                            isFirstResource: Boolean
                        ): Boolean {
                            if (e != null) {
                                Log.e("Glide loading failed VideoHorizontalItem", e.message.toString())
                            }
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable,
                            model: Any,
                            target: Target<Drawable>?,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.loader.visibility = GONE
                            return false
                        }

                    })
                    .into(thumbnail)
                setClickListeners(model.actionClickListener, model.itemClickListener)
            }
        }

        private fun setClickListeners(actionListener: ClickListener, itemListener: ClickListener) {
            binding.actionButton.setOnClickListener { actionListener.onClick() }
            binding.item.setOnClickListener { itemListener.onClick() }
        }
    }

}