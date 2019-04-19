package com.sport.viewmodels

import androidx.lifecycle.ViewModel
import com.sport.data.repository.SportInfoRepository
import com.sport.ui.fragment.ComparedFragment
import java.util.*

/**
 * User: bizehao
 * Date: 2019-03-14
 * Time: 上午9:45
 * Description: The ViewModel for [ComparedFragment].
 */
class ComparedViewModel internal constructor(
    private val sportInfoRepository: SportInfoRepository
) : ViewModel() {

    fun getSportInfoByTime(time:Date) = sportInfoRepository.getSportInfoByTime(time)

    fun getSportInfos() = sportInfoRepository.getSportInfos()

}
