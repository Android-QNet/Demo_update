package com.demo.android.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demo.android.data.models.Person



@Database(entities = [Person::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}