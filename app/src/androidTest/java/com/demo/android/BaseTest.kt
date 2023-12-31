package com.demo.android

import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import com.demo.android.data.Either
import com.demo.android.data.contract.HomeRepo
import com.demo.android.data.database.AppDao
import com.demo.android.data.database.AppDatabase
import com.demo.android.data.datasource.HomeLocaDataSource
import com.demo.android.data.datasource.HomeRemoteDataSource
import com.demo.android.data.repository.HomeRepository
import com.demo.android.domain.exceptions.MyException
import com.demo.android.domain.network.HomeApiService
import com.demo.android.presentation.home.HomeViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

/**
 * Created by Capermint on 09,October,2019
 * Capermint technologies
 * android@caperminttechnologies.com
 */


open class BaseTest {

    var exceptionLiveData : MutableLiveData<MyException> = MutableLiveData()
    @Mock
    lateinit var homeRepo : HomeRepo
    @Mock
    lateinit var homeLocalDataSource: HomeLocaDataSource
    @Mock
    lateinit var homeRemoteDataSource: HomeRemoteDataSource
    @Mock
    lateinit var homeApiService : HomeApiService

    lateinit var homeRepository: HomeRepository
    lateinit var homeViewModel : HomeViewModel

    lateinit var appDatabase : AppDatabase
    lateinit var appDao: AppDao

    @Before
    fun testBeforeSetup(){

        appDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        appDao = appDatabase.appDao()

        homeRepository = HomeRepository(homeApiService, appDao)
        homeViewModel = HomeViewModel(homeRepo)

    }

    fun <T> postValue(either: Either<MyException, T>, successLiveData: MutableLiveData<T>) {
        either.either({
            exceptionLiveData.postValue(it)
        }, {
            successLiveData.postValue(it)
        })
    }

    fun <T> verifyBlocking(mock: T, f: suspend T.() -> Unit) {
        val m = Mockito.verify(mock)
        runBlocking { m.f() }
    }

    inline fun <reified T> mock() = Mockito.mock(T::class.java)
    inline fun <T> whenever(methodCall: T) : OngoingStubbing<T> =
        Mockito.`when`(methodCall)
}