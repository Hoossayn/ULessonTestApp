package com.example.ulessontestapp.data.sources.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ulessontestapp.data.model.entities.Subject

@Dao
interface SubjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSubjects(subjects: List<Subject>)

    @Query("SELECT * FROM subjects")
    fun getAllSubjects(): LiveData<List<Subject>>

    @Query("SELECT * FROM subjects WHERE id =:id")
    suspend fun getSubject(id: Long): Subject
}