package myapplication.android.vkvideoviewer.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val page: Int,
    val imageId: Int,
    val title: String,
    val previewUrl: String,
    val largeImageUrl: String,
    val views: Int,
    val downloads: Int
)