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

    val currentPosition = MutableLiveData<Int>()

    val nextPosition = MutableLiveData<Int>()

}