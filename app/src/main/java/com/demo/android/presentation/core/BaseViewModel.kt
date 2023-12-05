package com.demo.android.presentation.core



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.demo.android.data.Either
import com.demo.android.data.models.User
import com.demo.android.domain.exceptions.MyException
import com.demo.android.presentation.utility.PrefKeys
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {

    val exceptionLiveData: MutableLiveData<Exception> = MutableLiveData()

    var user : User? = null

    var job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    init {
        getUserProfile()
    }

    /**
     * For separate the success and exception from network call
     *
     * @param T - Generic type of your class
     * @param either - your either which have success or exception
     * @param successLiveData - your success livedata
     */
    fun <T> postValue(either: Either<MyException, T>, successLiveData: MutableLiveData<T>) {
        either.either({
            exceptionLiveData.postValue(it)
        }, {
            successLiveData.postValue(it)
        })
    }


    fun getUserProfile(){
        val userProfile = Prefs.getString(PrefKeys.UserProfile, "")
        if (userProfile.isNotEmpty()) {
            user = Gson().fromJson(userProfile, User::class.java)
        }
    }
}