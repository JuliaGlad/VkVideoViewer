package myapplication.android.vkvideoviewer.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import myapplication.android.vkvideoviewer.data.database.entities.VideoEntity

@Dao
interface VideoDao {

    @Query("SELECT * FROM videos")
    fun getVideos(): List<VideoEntity>

    @Insert
    fun insertAll(videoList: List<VideoEntity>)

    @Insert
    fun insertVideo(video: VideoEntity)

    @Query("DELETE FROM videos")
    fun deleteAll()

    @Delete
    fun deleteVideo(video: VideoEntity)
}