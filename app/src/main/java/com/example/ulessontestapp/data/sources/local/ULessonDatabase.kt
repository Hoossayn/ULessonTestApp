package com.example.ulessontestapp.data.sources.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ulessontestapp.data.model.entities.RecentlyWatched
import com.example.ulessontestapp.data.model.entities.Subject
import com.example.ulessontestapp.data.sources.local.dao.RecentlyWatchedDao
import com.example.ulessontestapp.data.sources.local.dao.SubjectDao
import com.example.ulessontestapp.util.Converter

@Database(
    entities = [Subject::class, RecentlyWatched::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converter::class)
abstract class ULessonDatabase : RoomDatabase() {

    abstract fun subjectDao(): SubjectDao
    abstract fun recentWatchDao(): RecentlyWatchedDao

    companion object {
        private var instance: ULessonDatabase? = null

        fun getDatabase(context: Context): ULessonDatabase {
            if (instance == null) {
                synchronized(ULessonDatabase::class.java) {}
                instance =
                    Room.databaseBuilder(context, ULessonDatabase::class.java, "AppDatabase")
                        .fallbackToDestructiveMigration()
                        .build()
            }

            return instance!!
        }
    }
}

