package com.sport.ui.compared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sport.data.repository.SportInfoRepository

/**
 * User: bizehao
 * Date: 2019-03-14
 * Time: 上午10:25
 * Description: Factory for creating a [ComparedViewModel] with a constructor that takes a [SportInfoRepository].
 */
class ComparedViewModelFactory(
    private val repository: SportInfoRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ComparedViewModel(repository) as T
    }
}