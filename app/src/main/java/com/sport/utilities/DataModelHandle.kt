package com.sport.utilities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sport.R
import com.sport.data.Resource
import com.sport.data.table.SportData

/**
 * User: bizehao
 * Date: 2019-04-04
 * Time: 上午11:19
 * Description: 取数据 是否正在加载
 */
object DataModelHandle {

    //放入数据 返回LiveData型数据 无参
    fun <T> getData(myList: () -> T): LiveData<Resource<T>> {
        val liveDate = MutableLiveData<Resource<T>>()
        liveDate.value = Resource.loading(null)
        SportExecutors.diskIO.execute {
            val list = myList()
            liveDate.postValue(Resource.success(list))
        }
        return liveDate
    }

    //放入数据 返回LiveData型数据 有参
    fun <T, Y> getData(y: Y, myList: (y: Y) -> T): LiveData<Resource<T>> {
        val liveDate = MutableLiveData<Resource<T>>()
        liveDate.value = Resource.loading(null)
        SportExecutors.diskIO.execute {
            val list = myList(y)
            liveDate.postValue(Resource.success(list))
        }
        return liveDate
    }
}