package com.sport.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sport.data.Resource
import com.sport.data.repository.SportDataRepository
import com.sport.data.table.SportData
import com.sport.model.GroupSport
import com.sport.model.Sport
import com.sport.utilities.SharePreferencesUtil
import com.sport.utilities.USER_CURRENT_ITEM

/**
 * User: bizehao
 * Date: 2019-03-29
 * Time: 下午2:15
 * Description: 当前运动的界面
 */
class RunningViewModel(private val sportDataRepository: SportDataRepository) : ViewModel() {

    val intervalTime = MutableLiveData<Int>() //间隔时间 倒计时
    val sportNum = MutableLiveData<Int>() //俯卧撑数量
    val sportTime = MutableLiveData<Boolean>() //开始俯卧撑时进行的计时
    val totalSportNum = MutableLiveData<Int>() //俯卧撑的总数量

    //获取当前的运动信息
    fun getCurrentSport(): LiveData<Resource<SportData>> {
        val id = SharePreferencesUtil.read(USER_CURRENT_ITEM, 0)
        return sportDataRepository.getSportDataById(id)
    }

}