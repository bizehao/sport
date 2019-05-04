package com.sport.viewmodels

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sport.data.Resource
import com.sport.data.repository.PlantRepository
import com.sport.data.repository.SportDataRepository
import com.sport.data.table.Plant
import com.sport.data.table.SportData
import com.sport.utilities.SharePreferencesUtil
import com.sport.utilities.SportExecutors
import com.sport.utilities.USER_CURRENT_ITEM
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

/**
 * User: bizehao
 * Date: 2019-03-22
 * Time: 下午2:59
 * Description:
 */
class IndexViewModel(
    private val plantRepository: PlantRepository,
    private val sportDataRepository: SportDataRepository
) : ViewModel() {

    //日志数据
    val plants: LiveData<List<Plant>> = plantRepository.getPlants()

    //距目标时间
    val dateTimeOfLast: LiveData<String> = sportDataRepository.getDateTimeOfLast()

    //获取多少个等级
    fun getSportDataCount() = sportDataRepository.getCountOfSportData()

    //界面数据的处理
    fun handleCurrentInfo(currentPosition: Int): LiveData<Map<String, Any>> {

        //界面数据集
        val dataMap = MutableLiveData<Map<String, Any>>()

        SportExecutors.diskIO.execute {
            val list1 = sportDataRepository.getCompletedSportData(currentPosition)
            val list2 = sportDataRepository.getSurplusSportData(currentPosition)
            val sportData = sportDataRepository.getSportDataByIndex(currentPosition)

            var totalCompletedPushUpNum = 0
            for (l1 in list1) {
                for (s1 in l1.subData) {
                    totalCompletedPushUpNum += s1.pushUpNum
                }
            }

            var totalSurplusPushUpNum = 0
            for (l2 in list2) {
                for (s2 in l2.subData) {
                    totalSurplusPushUpNum += s2.pushUpNum
                }
            }

            val map = HashMap<String, Any>()
            map["totalCompletedPushUpNum"] = totalCompletedPushUpNum //已完成的总数量
            map["totalSurplusPushUpNum"] = totalSurplusPushUpNum //剩余总数量
            map["nextData"] = sportData //剩余总数量
            dataMap.postValue(map)
        }
        return dataMap;
    }

}