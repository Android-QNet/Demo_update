package com.demo.android.data.datasource



import com.demo.android.data.Either
import com.demo.android.data.models.Person
import com.demo.android.domain.exceptions.MyException

interface HomeLocaDataSource {
    suspend fun getPersonList(): Either<MyException, List<Person>>
    suspend fun insertPersonList(list: List<Person>): Either<MyException, Boolean>
}