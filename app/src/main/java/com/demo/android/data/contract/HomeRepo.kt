package com.demo.android.data.contract



import com.demo.android.data.Either
import com.demo.android.data.models.*
import com.demo.android.domain.exceptions.MyException

interface HomeRepo {


    suspend fun getPersonListLocal(): Either<MyException, List<Person>>
    suspend fun insertPersonList(list : List<Person>) : Either<MyException, Boolean>


    suspend fun performLogout(logoutPRQ: LogoutPRQ) : Either<MyException, BaseResponse>

    suspend fun getCMSPageData(cmsPagePRQ: CMSPagePRQ): Either<MyException, CMSPageRS>
    suspend fun getFaqs(): Either<MyException, FAQDataListRS>
    suspend fun performContactUs(contactUsPRQ: ContactUsPRQ): Either<MyException, BaseResponse>

}