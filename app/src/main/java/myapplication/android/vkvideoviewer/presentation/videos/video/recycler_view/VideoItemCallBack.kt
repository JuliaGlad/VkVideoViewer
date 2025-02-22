package myapplication.android.vkvideoviewer.presentation.videos.video.recycler_view

import androidx.recyclerview.widget.DiffUtil

class VideoItemCallBack(): DiffUtil.ItemCallback<VideoItemModel>() {
    override fun areItemsTheSame(oldItem: VideoItemModel, newItem: VideoItemModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: VideoItemModel, newItem: VideoItemModel): Boolean =
        oldItem == newItem
}