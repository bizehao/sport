package com.sport.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sport.data.Resource
import com.sport.data.dao.SportInfoDao
import com.sport.data.table.SportInfo
import com.sport.utilities.SportExecutors
import java.util.*
import kotlin.math.sign

/**
 * User: bizehao
 * Date: 2019-03-13
 * Time: 上午9:29
 * Description: 运动信息的数据仓库
 */
class SportInfoRepository private constructor(private val sportInfoDao: SportInfoDao) {

    /**
     * 获取所有运动信息
     */
    fun getSportInfos(): LiveData<Resource<List<SportInfo>>> {
        val liveDate = MutableLiveData<Resource<List<SportInfo>>>()
        liveDate.value = Resource.loading(null)
        SportExecutors.diskIO.execute{
            val list = sportInfoDao.getSportInfos()
            liveDate.postValue(Resource.success(list))
        }
        return liveDate
    }

    /**
     * 根据时间获取所有运动信息
     */
    fun getSportInfoByTime(time:Date): LiveData<Resource<SportInfo>> {
        val liveDate = MutableLiveData<Resource<SportInfo>>()
        liveDate.value = Resource.loading(null)
        SportExecutors.diskIO.execute{
            val list = sportInfoDao.getSportInfoByTime(time)
            liveDate.postValue(Resource.success(list))
        }
        return liveDate
    }

    /**
     * 添加运动信息
     */
    fun insertSportInfo(sportInfo: SportInfo):LiveData<Resource<Int>> {
        val liveData = MutableLiveData<Resource<Int>>()
        liveData.value = Resource.loading(null)
        SportExecutors.diskIO.execute{
            sportInfoDao.insertSportInfo(sportInfo)
            liveData.postValue(Resource.success(1))
        }
        return liveData
    }

    companion object {

        @Volatile
        private var instance: SportInfoRepository? = null;

        fun getInstance(sportInfoDao: SportInfoDao) =
            instance ?: synchronized(this) {
                instance ?: SportInfoRepository(sportInfoDao).also { instance = it }
            }
    }
}