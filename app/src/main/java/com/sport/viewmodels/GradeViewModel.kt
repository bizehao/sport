package com.sport.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sport.data.repository.SportDataRepository
import com.sport.data.table.SportData
import com.sport.model.Sport
import com.sport.utilities.SharePreferencesUtil
import com.sport.utilities.USER_CURRENT_ITEM
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

    private val timeFormat = SimpleDateFormat("MM-dd", Locale.CHINA)

    private val sportListOfLiveData = MutableLiveData<ArrayList<Sport>>() //视图列表的运动列表数据

    val sportMapOfLiveData = MutableLiveData<HashMap<Int, Int>>() //视图列表的运动列表数据

    fun getSportData() = repository.getSportData() //获取数据库里的运动列表数据

    fun getSportViewData() = sportListOfLiveData

    fun setSportViewData(data: List<SportData>?) {
        val currentPosition = SharePreferencesUtil.read(USER_CURRENT_ITEM, 0)
        if(data != null){
            val sportList: ArrayList<Sport> = ArrayList()
            val map1: HashMap<Int, Int> = HashMap()
            var nn = 0
            var mSport: Sport
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, -2)
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
                addTime(mSport, calendar, currentPosition)
                sportList.add(mSport)
                val xx = if (map1[s.grade] == null) 0 else map1[s.grade]
                if (xx != null) {
                    map1[s.grade] = xx + 1
                }
            }
            sportMapOfLiveData.value = map1
            sportListOfLiveData.value = sportList
        }else{
            val sportList = sportListOfLiveData.value
            if(sportList != null){
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DATE, -2)
                for (mSport in sportList){
                    addTime(mSport, calendar, currentPosition)
                }
                sportListOfLiveData.value = sportList
            }
        }
    }

    private fun addTime(sport: Sport, calendar: Calendar, position: Int) {
        if (sport.id >= position) {
            calendar.add(Calendar.DATE, 2)
            if (sport.id == position) {
                sport.dateTime = "今天"
                sport.current = true
            } else {
                sport.dateTime = timeFormat.format(calendar.time)
                sport.current = false
            }
        } else {
            sport.dateTime = ""
            sport.current = false
        }
    }
}