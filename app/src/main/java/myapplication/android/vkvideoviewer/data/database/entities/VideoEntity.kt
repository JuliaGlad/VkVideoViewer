package myapplication.android.vkvideoviewer.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import myapplication.android.vkvideoviewer.data.api.models.VideoQuality

@Entity(tableName = "videos")
class VideoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val page: Int,
    val videoId: Int,
    val title: String,
    val duration: Int,
    val large: VideoQuality,
    val medium: VideoQuality,
    val small: VideoQuality,
    val tiny: VideoQuality
)