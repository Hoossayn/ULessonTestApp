package com.example.ulessontestapp.util

import com.example.ulessontestapp.data.model.Chapter
import com.example.ulessontestapp.data.model.Data
import com.example.ulessontestapp.data.model.Lesson
import com.example.ulessontestapp.data.model.SubjectResponse
import com.example.ulessontestapp.data.model.entities.RecentlyWatched
import com.example.ulessontestapp.data.model.entities.Subject

object TestObjectUtil {
    val lessons = listOf(

        Lesson("icon1.png",1,"lesson1","lesson1.mp4",3L,12L),
        Lesson("icon1.png",2,"lesson2","lesson2.mp4",3L,12L),
        Lesson("icon1.png",3,"lesson3","lesson3.mp4",3L,12L),
        Lesson("icon1.png",4,"lesson4","lesson4.mp4",3L,12L),

    )

    val chapters = listOf(
        Chapter(14L, lessons,"Gravity", )
    )

    val subjects = listOf(

        Subject(chapters,"physics.png", 4L,"Physics" ),
        Subject(chapters,"chem.png", 5L,"Physics" ),
        Subject(chapters,"biology.png", 6L,"Physics" ),
        Subject(chapters,"math.png", 7L,"Physics" ),

    )

    val recentViews = listOf(
        RecentlyWatched(3L, "Physics", "Gravity","lesson1.mp4"),
        RecentlyWatched(4L, "Chemistry", "Gravity","lesson1.mp4"),
        RecentlyWatched(5L, "Biology", "Gravity","lesson1.mp4"),
        RecentlyWatched(6L, "English", "Adjective","lesson1.mp4")
    )

    val subjectDataResponse = SubjectResponse(
        Data(status = "true", message = "connection_ok",subjects = subjects)
    )
}