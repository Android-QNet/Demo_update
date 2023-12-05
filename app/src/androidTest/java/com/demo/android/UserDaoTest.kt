package com.demo.android

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.demo.android.data.database.AppDao
import com.demo.android.data.database.AppDatabase
import com.demo.android.data.models.User
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Capermint on 07,October,2019
 * Capermint technologies
 * android@caperminttechnologies.com
 */

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    lateinit var appDatabase : AppDatabase
    lateinit var appDao: AppDao

    @Before()
    fun beforeSetup(){
        appDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        appDao = appDatabase.appDao()
    }

    @Test
    fun testUserInsert(){
        val user = User(
            1,
            "abc.png",
            "abc@test.com",
            "Android",
            "test"
        )
        appDao.insertUserList(arrayListOf(user))

        val dbList = appDao.getUserList()
        assert(dbList.isNotEmpty())
    }

}