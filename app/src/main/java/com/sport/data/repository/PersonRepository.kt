package com.sport.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sport.data.Resource
import com.sport.data.dao.PersonDao
import com.sport.data.table.Person
import com.sport.utilities.DataModelHandle
import com.sport.utilities.SportExecutors

/**
 * User: bizehao
 * Date: 2019-03-12
 * Time: 下午2:16
 * Description:
 */
class PersonRepository private constructor(private val personDao: PersonDao) {
    fun getPersons() = DataModelHandle.getData { personDao.getPersons() }

    fun insertPersons(persons: List<Person>) {
        SportExecutors.diskIO.execute {
            personDao.insertAll(persons)
        }
    }

    fun getPersonByName(name: String) = personDao.getPersonByName(name)

    companion object {

        @Volatile
        private var instant: PersonRepository? = null

        fun getInstance(personDao: PersonDao) =
            instant ?: synchronized(this) {
                instant ?: PersonRepository(personDao).also { instant = it }
            }
    }
}