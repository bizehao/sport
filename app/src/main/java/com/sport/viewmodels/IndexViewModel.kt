package com.sport.viewmodels

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sport.data.repository.PlantRepository
import com.sport.data.table.Plant
import com.sport.data.table.SportData
import com.sport.utilities.SharePreferencesUtil
import com.sport.utilities.USER_CURRENT_ITEM
import java.text.SimpleDateFormat
import java.util.*

/**
 * User: bizehao
 * Date: 2019-03-22
 * Time: 下午2:59
 * Description:
 */
class IndexViewModel(private val plantRepository: PlantRepository) :ViewModel(){

    val plants: LiveData<List<Plant>> = plantRepository.getPlants()

    /*fun getTomorroySportData():LiveData<SportData>{
        val currentPosition = SharePreferencesUtil.read(USER_CURRENT_ITEM, 0)
    }*/
}