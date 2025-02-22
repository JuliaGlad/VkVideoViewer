package myapplication.android.vkvideoviewer.presentation.images.images.recycler_view

import androidx.recyclerview.widget.DiffUtil

class ImageItemCallBack(): DiffUtil.ItemCallback<ImageItemModel>() {
    override fun areItemsTheSame(oldItem: ImageItemModel, newItem: ImageItemModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ImageItemModel, newItem: ImageItemModel): Boolean =
        oldItem == newItem
}