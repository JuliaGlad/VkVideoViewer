package myapplication.android.vkvideoviewer.data.database.entities.saved

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved")
class SavedEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val itemId: Int,
    val type: SavedType
)