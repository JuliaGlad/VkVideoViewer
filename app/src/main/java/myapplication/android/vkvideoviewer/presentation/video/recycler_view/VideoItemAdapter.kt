package myapplication.android.vkvideoviewer.presentation.video.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
                viewsCount.text = model.views.toString()
                downloadsCount.text = model.downloads.toString()
                duration.text = "00:${model.duration}"
                Glide.with(itemView.context)
                    .load(model.thumbnail.toUri())
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