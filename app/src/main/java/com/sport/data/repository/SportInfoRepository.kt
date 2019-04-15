package com.sport.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sport.data.Resource
import com.sport.data.dao.SportInfoDao
import com.sport.data.table.SportInfo
import com.sport.utilities.DataModelHandle
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
    fun getSportInfos() = DataModelHandle.getData { sportInfoDao.getSportInfos() }

    /**
     * 根据时间获取所有运动信息
     */
    fun getSportInfoByTime(time: Date) = DataModelHandle.getData { sportInfoDao.getSportInfoByTime(time) }

    /**
     * 添加运动信息
     */
    fun insertSportInfo(sportInfo: SportInfo) = DataModelHandle.getData(sportInfo) {
        sportInfoDao.insertSportInfo(sportInfo)
    }

    companion object {

        @Volatile
        private var instance: SportInfoRepository? = null

        fun getInstance(sportInfoDao: SportInfoDao) =
            instance ?: synchronized(this) {
                instance ?: SportInfoRepository(sportInfoDao).also { instance = it }
            }
    }
}