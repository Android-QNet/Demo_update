package com.demo.android.di



import com.demo.android.data.contract.HomeRepo
import com.demo.android.data.contract.LoginSignupRepo
import com.demo.android.data.repository.HomeRepository
import com.demo.android.data.repository.LoginSignupApisRepository
import org.koin.dsl.module.module

/**
 * Created Koin module for Repository class
 */

val repositoryModule = module {
    single<LoginSignupRepo> { LoginSignupApisRepository(get()) }
    single<HomeRepo> { HomeRepository(get(), get()) }
}