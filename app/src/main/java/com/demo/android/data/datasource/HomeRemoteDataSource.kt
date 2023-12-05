package com.demo.android.data.datasource



import com.demo.android.data.Either
import com.demo.android.data.models.PersonListPRQ
import com.demo.android.data.models.PersonListRS
import com.demo.android.domain.exceptions.MyException

interface HomeRemoteDataSource {
    suspend fun getPersonList(personListPRQ: PersonListPRQ): Either<MyException, PersonListRS>
}