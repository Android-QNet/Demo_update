package com.demo.android.presentation.home



import androidx.lifecycle.MutableLiveData
import com.demo.android.data.contract.HomeRepo
import com.demo.android.data.models.*
import com.demo.android.presentation.core.BaseViewModel
import com.demo.android.presentation.utility.PrefKeys
import kotlinx.coroutines.launch

class HomeViewModel constructor(private val homeRepo: HomeRepo) : BaseViewModel() {

    val personLocalListRSLiveData: MutableLiveData<List<Person>> = MutableLiveData()
    val insertPersonListRSLiveData: MutableLiveData<Boolean> = MutableLiveData()



    fun insertPersonList(personList: List<Person>) {
        launch {
            postValue(homeRepo.insertPersonList(personList), insertPersonListRSLiveData)
        }
    }

    fun getPersonListLocal() {
        launch {
            postValue(homeRepo.getPersonListLocal(), personLocalListRSLiveData)
        }
    }

    val logoutRSLiveData: MutableLiveData<BaseResponse> = MutableLiveData()
    val cmsPageRSLiveData: MutableLiveData<CMSPageRS> = MutableLiveData()
    val faqDataListRSLiveData : MutableLiveData<FAQDataListRS> = MutableLiveData()
    val contactUsRSLiveData: MutableLiveData<BaseResponse> = MutableLiveData()

    fun performLogout() {
        val logoutPRQ = LogoutPRQ(
            PrefKeys.getAuthKey()
        )
        launch {
            postValue(homeRepo.performLogout(logoutPRQ), logoutRSLiveData)
        }
    }

    fun getCMSPageData(page_code : String){
        val cmsPagePRQ = CMSPagePRQ(
            page_code
        )
        launch {
            postValue(homeRepo.getCMSPageData(cmsPagePRQ), cmsPageRSLiveData)
        }
    }

    fun getFAQs(){
        launch {
            postValue(homeRepo.getFaqs(), faqDataListRSLiveData)
        }
    }


}