package myapplication.android.vkvideoviewer.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import myapplication.android.vkvideoviewer.data.database.entities.ImageEntity
import myapplication.android.vkvideoviewer.data.database.entities.VideoEntity

@Dao
interface ImageDao {

    @Query("SELECT * FROM images")
    fun getImages(): List<ImageEntity>

    @Insert
    fun insertAll(imageList: List<ImageEntity>)

    @Insert
    fun insertImage(video: ImageEntity)

    @Query("DELETE FROM images")
    fun deleteAll()

    @Delete
    fun deleteImage(video: ImageEntity)

}