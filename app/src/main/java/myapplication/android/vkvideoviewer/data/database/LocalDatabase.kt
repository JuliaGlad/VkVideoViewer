package myapplication.android.vkvideoviewer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import myapplication.android.vkvideoviewer.data.database.converter.VideoQualityConverter
import myapplication.android.vkvideoviewer.data.database.dao.VideoDao
import myapplication.android.vkvideoviewer.data.database.entities.VideoEntity

@TypeConverters(
    value = [
        VideoQualityConverter::class
    ]
)
@Database(
    entities = [
        VideoEntity::class,
    ], version = 1
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun videoDao() : VideoDao
}