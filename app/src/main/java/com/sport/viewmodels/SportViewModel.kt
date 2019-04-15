package com.sport.viewmodels;

import androidx.lifecycle.ViewModel
import com.sport.data.repository.PersonRepository
import com.sport.data.repository.SportInfoRepository
import com.sport.data.table.Person
import com.sport.data.table.SportInfo
import com.sport.ui.fragment.SportFragment

/**
 * User: bizehao
 * Date: 2019-03-14
 * Time: 上午9:44
 * Description: The ViewModel for [SportFragment].
 */
class SportViewModel internal constructor(
    private val sportInfoRepository: SportInfoRepository,
    private val personRepository: PersonRepository
) : ViewModel() {

    fun insertSportInfo(sportInfo: SportInfo) = sportInfoRepository.insertSportInfo(sportInfo)

    fun getPerson() = personRepository.getPersons()

    fun insertPerson(persons: List<Person>) = personRepository.insertPersons(persons)
}
