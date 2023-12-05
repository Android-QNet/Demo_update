package com.demo.android.di



import androidx.room.Room
import com.demo.android.data.database.AppDatabase
import com.demo.android.presentation.utility.AppConstant
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module


val roomModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            AppConstant.DB_NAME
        ).build()
    }

    single { get<AppDatabase>().appDao() }

}