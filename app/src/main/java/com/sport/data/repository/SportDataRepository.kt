package com.sport.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sport.data.Resource
import com.sport.data.dao.SportDataDao
import com.sport.data.table.SportData
import com.sport.data.table.SportInfo
import com.sport.utilities.DataModelHandle
import com.sport.utilities.SportExecutors

/**
 * User: bizehao
 * Date: 2019-04-04
 * Time: 上午11:06
 * Description:
 */
class SportDataRepository private constructor(private val sportDataDao: SportDataDao) {

    fun getSportData() = DataModelHandle.getData { sportDataDao.getSportData() }

    fun getSportDataById(id: Int) = DataModelHandle.getData { sportDataDao.getSportDataById(id) }

    fun getSportDataByIndex(id: Int) = sportDataDao.getSportDataById(id)


    fun getCountOfSportData() = sportDataDao.getCountOfSportData()

    fun getDateTimeOfLast() = sportDataDao.getDateTimeOfLast()

    fun getCompletedSportData(id: Int) = sportDataDao.getCompletedSportData(id)

    fun getSurplusSportData(id: Int) = sportDataDao.getSurplusSportData(id)

    fun updateSportData(list: List<SportData>) {
        sportDataDao.updateAll(list)
    }

    companion object {
        @Volatile
        private var instance: SportDataRepository? = null

        fun getInstance(sportDataDao: SportDataDao): SportDataRepository {
            return instance ?: synchronized(this) {
                instance ?: SportDataRepository(sportDataDao).also { instance = it }
            }
        }
    }
}