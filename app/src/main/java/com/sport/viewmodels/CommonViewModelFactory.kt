package com.sport.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sport.data.repository.SportInfoRepository

/**
 * User: bizehao
 * Date: 2019-03-14
 * Time: 上午10:25
 * Description: Factory for creating a ViewModel with a constructor that takes a Repository.
 * 构建一个带有repository的viewModel
 */
class CommonViewModelFactory<TYPE : ViewModel>(
    private var viewModel: TYPE
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModel as T
    }
}