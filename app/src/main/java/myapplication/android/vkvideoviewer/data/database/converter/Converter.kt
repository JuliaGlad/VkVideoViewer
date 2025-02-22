package myapplication.android.vkvideoviewer.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import myapplication.android.vkvideoviewer.data.api.models.VideoQuality
import myapplication.android.vkvideoviewer.data.database.entities.saved.SavedType

abstract class Converter<T>(
    private val typeToken: TypeToken<T>
) {
    private val gson = Gson()
    @TypeConverter
    fun toJson(value: T): String {
        return gson.toJson(value, typeToken.type)
    }

    @TypeConverter
    fun fromJson(json: String): T {
        return gson.fromJson(json, typeToken)
    }
}

class VideoQualityConverter : Converter<VideoQuality?>(TypeToken.get(VideoQuality::class.java))

class SavedTypeConverter: Converter<SavedType>(TypeToken.get(SavedType::class.java))