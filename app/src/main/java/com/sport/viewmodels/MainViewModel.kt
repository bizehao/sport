package com.sport.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * User: bizehao
 * Date: 2019-04-22
 * Time: 上午11:48
 * Description:
 */
class MainViewModel : ViewModel() {

    //当前的等级
    val currentPosition = MutableLiveData<Int>()

}