# ULessonTestApp

# Summary

 - Use MVVM using architecture components with to separate Android Framework with a clean architecture to my domain logic.
 - Use Android Databinding wih LiveData to glue ViewModel and Android
 - Asynchronous communications implemented with KotlinX Coroutines.
 - Rest API from ComicVine
 - Store data using Room

# why mvvm 
 - ViewModel has Built in LifeCycleOwerness, on the other hand Presenter not, and you have to take this responsiblty in your side.
 - ViewModel doesn't have a reference for View, on the other hand Presenter still hold a reference for view, even if you made it as weakreference.
 - ViewModel survive configuration changes, while it is your own responsiblities to survive the configuration changes in case of Presenter. (Saving and restoring the UI state)
 

# Dependencies
 - architecture components
    - livedata
    - room
    - viewmodel
 - kotlinx coroutines
 - Okhttp
 - retrofit
 - Hilt
 - Exo player
 - Glide
