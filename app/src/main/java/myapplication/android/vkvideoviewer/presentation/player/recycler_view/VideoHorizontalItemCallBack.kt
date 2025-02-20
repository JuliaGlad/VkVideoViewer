package myapplication.android.vkvideoviewer.presentation.player.recycler_view

import androidx.recyclerview.widget.DiffUtil

class VideoHorizontalItemCallBack: DiffUtil.ItemCallback<VideoHorizontalItemModel>() {
    override fun areItemsTheSame(oldItem: VideoHorizontalItemModel, newItem: VideoHorizontalItemModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: VideoHorizontalItemModel, newItem: VideoHorizontalItemModel): Boolean =
        oldItem == newItem
}