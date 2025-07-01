package ru.fefu.helloworld

import android.content.Context
import androidx.lifecycle.LiveData
import android.util.Log

class ActivityRepository(context: Context) {
    private val activityDao = AppDatabase.getDatabase(context).activityDao()

    fun getAllActivities(): LiveData<List<ActivityEntity>> = activityDao.getAllActivities()

    suspend fun insert(activity: ActivityEntity) {
        Log.d("ActivityRepository", "insert called: $activity")
        activityDao.insert(activity)
    }

    suspend fun deleteAll() = activityDao.deleteAll()

    suspend fun deleteById(id: Int) {
        Log.d("ActivityRepository", "deleteById called: $id")
        activityDao.deleteById(id)
    }
} 