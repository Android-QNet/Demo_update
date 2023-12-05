package com.demo.android.data.repository



import com.demo.android.data.BaseRepository
import com.demo.android.data.Either
import com.demo.android.data.contract.HomeRepo
import com.demo.android.data.database.AppDao
import com.demo.android.data.models.*
import com.demo.android.domain.exceptions.MyException
import com.demo.android.domain.network.HomeApiService

class HomeRepository constructor(
    private val homeApiService: HomeApiService,
    private val appDao: AppDao
) : HomeRepo, BaseRepository() {



    override suspend fun getPersonListLocal(): Either<MyException, List<Person>> {
        return either {
            appDao.getPersonList()
        }
    }

    override suspend fun insertPersonList(list: List<Person>): Either<MyException, Boolean> {
        appDao.insertPersonList(list)
        return either {
            true
        }
    }

    override suspend fun performLogout(logoutPRQ: LogoutPRQ): Either<MyException, BaseResponse> {
        return either {
            homeApiService.performLogout(
                logoutPRQ.authKey
            )
        }
    }

    override suspend fun getCMSPageData(cmsPagePRQ: CMSPagePRQ): Either<MyException, CMSPageRS> {
        return either {
            homeApiService.getCMSPages(
                cmsPagePRQ.page_code.orEmpty()
            )
        }
    }

    override suspend fun getFaqs(): Either<MyException, FAQDataListRS> {
        return either {
            homeApiService.getFaqs()
        }
    }

    override suspend fun performContactUs(contactUsPRQ: ContactUsPRQ): Either<MyException, BaseResponse> {
        return either {
            homeApiService.performContactUs(
                contactUsPRQ.authKey,
                contactUsPRQ.contact_email.orEmpty(),
                contactUsPRQ.feedback.orEmpty(),
                contactUsPRQ.vAppVersion.orEmpty(),
                contactUsPRQ.eDeviceType.orEmpty(),
                contactUsPRQ.vDeviceName.orEmpty()
            )
        }
    }

}