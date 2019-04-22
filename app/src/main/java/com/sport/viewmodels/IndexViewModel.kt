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

    val plants: LiveData<List<Plant>> = plantRepository.getPlants()

    //距目标时间
    val dateTimeOfLast: LiveData<String> = sportDataRepository.getDateTimeOfLast()

    //已完成
    val completed = MutableLiveData<Int>()

    //还剩余
    val surplus = MutableLiveData<Int>()

    //下一次训练
    var nextSport  = MutableLiveData<SportData>()

    fun getSportDataCount() = sportDataRepository.getCountOfSportData()

    fun handleNextInfo(nextPosition:Int){
        SportExecutors.diskIO.execute {
            val sport = sportDataRepository.getSportDataByIndex(nextPosition)
            nextSport.postValue(sport)
        }
    }

    fun handleCurrentInfo(currentPosition: Int) {
        SportExecutors.diskIO.execute {
            val list1 = sportDataRepository.getCompletedSportData(currentPosition)
            val list2 = sportDataRepository.getSurplusSportData(currentPosition)

            var totalCompletedPushUpNum = 0
            for (l1 in list1) {
                for (s1 in l1.subData) {
                    totalCompletedPushUpNum += s1.pushUpNum
                }
            }
            completed.postValue(totalCompletedPushUpNum)

            var totalSurplusPushUpNum = 0
            for (l2 in list2) {
                for (s2 in l2.subData) {
                    totalSurplusPushUpNum += s2.pushUpNum
                }
            }
            surplus.postValue(totalSurplusPushUpNum)
        }
    }

}