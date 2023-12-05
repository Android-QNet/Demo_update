package com.demo.android.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.android.data.models.Person



@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPersonList(list: List<Person>)

    @Query("SELECT * FROM users")
    fun getPersonList(): List<Person>

}