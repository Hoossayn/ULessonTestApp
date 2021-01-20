package com.example.ulessontestapp.data.sources.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ulessontestapp.data.model.entities.RecentlyWatched

@Dao
interface RecentlyWatchedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRecentWatch(recentlyWatched: RecentlyWatched)

    @Query("SELECT * FROM recentlyWatched LIMIT :id")
    fun getRecentWatch(id: Int): LiveData<List<RecentlyWatched>>
}