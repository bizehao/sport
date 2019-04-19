package com.sport.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sport.data.Resource
import com.sport.data.repository.SportDataRepository
import com.sport.data.table.SportData
import com.sport.model.Sport
import com.sport.utilities.SharePreferencesUtil
import com.sport.utilities.SportExecutors
import com.sport.utilities.USER_CURRENT_ITEM
import timber.log.Timber
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

/**
 * User: bizehao
 * Date: 2019-04-04
 * Time: 上午11:04
 * Description: 级别列表的数据
 */
class GradeViewModel internal constructor(private val repository: SportDataRepository) : ViewModel() {

    private lateinit var sportDataList: List<SportData> //数据库里的数据

    private val timeFormat = SimpleDateFormat("MM-dd", Locale.CHINA)

    private val sportListOfLiveData = MutableLiveData<ArrayList<Sport>>() //视图列表的运动列表数据

    private val sportMapOfLiveData = MutableLiveData<HashMap<Int, Int>>() //视图列表的等级划分数据

    //获取数据库里的数据
    fun getSportData() = repository.getSportData()

    fun getSportViewData() = sportListOfLiveData

    fun getSportMapOfLiveData() = sportMapOfLiveData

    //设置列表数据
    fun setSportViewData(data: List<SportData>) {
        val currentPosition = SharePreferencesUtil.read(USER_CURRENT_ITEM, 0)
        sportDataList = data
        val sportList: ArrayList<Sport> = ArrayList()
        val map1: HashMap<Int, Int> = HashMap()
        var nn = 0
        var mSport: Sport
        for (s in data) {
            mSport = Sport(
                s.id,
                true,
                s.grade,
                s.intervalTime,
                false
            )
            val sbd = StringBuilder()
            s.subData.forEach { itt ->
                sbd.append(itt.pushUpNum).append("-")
            }
            mSport.pushUpNum = sbd.delete(sbd.length - 1, sbd.length).toString()

            if (s.grade != nn) {
                nn = s.grade
                mSport.isStartOfGroup = true
            } else {
                mSport.isStartOfGroup = false
            }
            mSport.dateTime = s.dateTime
            if (mSport.id == currentPosition && mSport.dateTime != "") {
                mSport.current = true
            }
            sportList.add(mSport)
            val xx = if (map1[s.grade] == null) 0 else map1[s.grade]
            if (xx != null) map1[s.grade] = xx + 1
        }
        sportMapOfLiveData.value = map1
        sportListOfLiveData.value = sportList
    }

    //改变时间列表 当前项
    fun changeSportListOfTime(currentPosition: Int) {
        val sportList = sportListOfLiveData.value
        if (sportList != null) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, -2)
            for (s in sportDataList.indices) {
                if (sportDataList[s].id >= currentPosition) {
                    calendar.add(Calendar.DATE, 2)
                    sportDataList[s].dateTime = timeFormat.format(calendar.time)
                } else {
                    sportDataList[s].dateTime = ""
                }
                sportList[s].current = sportList[s].id == currentPosition
                sportList[s].dateTime = sportDataList[s].dateTime
            }
            SportExecutors.diskIO.execute {
                repository.updateSportData(sportDataList)
            }
            sportListOfLiveData.value = sportList
        }
    }
}