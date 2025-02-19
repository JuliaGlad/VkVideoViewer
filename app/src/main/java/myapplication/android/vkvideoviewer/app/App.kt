package myapplication.android.vkvideoviewer.app

import android.app.Application
import androidx.room.Room.databaseBuilder
import myapplication.android.vkvideoviewer.data.database.LocalDatabase

class App: Application() {

    val database: LocalDatabase by lazy { createDatabase() }

    override fun onCreate() {
        super.onCreate()
        app = this
    }

    private fun createDatabase() =
        databaseBuilder(this, LocalDatabase::class.java, "database")
            .allowMainThreadQueries()
            .build()

    companion object {
        internal lateinit var app: App
           private set
    }
}