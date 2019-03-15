package com.sport.ui.sport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sport.data.repository.PersonRepository
import com.sport.data.repository.SportInfoRepository
import com.sport.ui.compared.ComparedViewModel

/**
 * User: bizehao
 * Date: 2019-03-14
 * Time: 上午10:25
 * Description: Factory for creating a [SportViewModel] with a constructor that takes a [SportInfoRepository].
 */
class SportViewModelFactory(
    private val sportInfoRepository: SportInfoRepository,
    private val personRepository: PersonRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SportViewModel(sportInfoRepository,personRepository) as T
    }
}