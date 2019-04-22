/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sport.utilities

import android.content.Context
import com.sport.data.AppDatabase
import com.sport.data.repository.PersonRepository
import com.sport.data.repository.PlantRepository
import com.sport.data.repository.SportDataRepository
import com.sport.data.repository.SportInfoRepository
import com.sport.viewmodels.*


/**
 * static methods used to inject classes needed for various activities and fragments.
 */
object InjectorUtils {

    /**
     * 获取人员信息仓库
     */
    private fun getPersonRepository(context: Context): PersonRepository {
        return PersonRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).personDao()
        )
    }

    /**
     * 获取运动信息仓库
     */
    private fun getSportInfoRepository(context: Context): SportInfoRepository {
        return SportInfoRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).sportInfoDao()
        )
    }

    /**
     * 获取植物工厂
     */
    private fun getPlantRepository(context: Context): PlantRepository {
        return PlantRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).plantDao()
        )
    }

    /**
     * 获取运动数据工厂
     */
    private fun getSportDataRepository(context: Context): SportDataRepository {
        return SportDataRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).sportDataDao()
        )
    }

//数据工厂
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//viewModel构建工厂

    /**
     * 获取运动界面的ViewModel的构造工厂
     */
    fun provideSportViewModelFactory(context: Context): CommonViewModelFactory<SportViewModel> {
        val sportInfoRepository = getSportInfoRepository(context)
        val personRepository = getPersonRepository(context)
        return CommonViewModelFactory(SportViewModel(sportInfoRepository, personRepository))
    }

    /**
     * 获取运动信息展示的ViewModel的构造工厂
     */
    fun provideComparedViewModelFactory(context: Context): CommonViewModelFactory<ComparedViewModel> {
        val repository = getSportInfoRepository(context)
        return CommonViewModelFactory(ComparedViewModel(repository))
    }

    /**
     * 获取运动主界面的ViewModel的构造工厂
     */
    fun provideIndexViewModelFactory(context: Context): CommonViewModelFactory<IndexViewModel> {
        val plantRepository = getPlantRepository(context)
        val sportDataRepository = getSportDataRepository(context)
        return CommonViewModelFactory(IndexViewModel(plantRepository, sportDataRepository))
    }

    /**
     * 获取运动主界面的ViewModel的构造工厂
     */
    fun provideGradeViewModelFactory(context: Context): CommonViewModelFactory<GradeViewModel> {
        val repository = getSportDataRepository(context)
        return CommonViewModelFactory(GradeViewModel(repository))
    }

    /**
     * 获取运动主界面的ViewModel的构造工厂
     */
    fun provideRunningViewModelFactory(context: Context): CommonViewModelFactory<RunningViewModel> {
        val repository = getSportDataRepository(context)
        return CommonViewModelFactory(RunningViewModel(repository))
    }


}
