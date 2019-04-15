package com.sport.data.dao

import androidx.room.*
import com.sport.data.table.SportData

/**
 * User: bizehao
 * Date: 2019-04-03
 * Time: 下午1:56
 * Description: 俯卧撑的训练日
 */
@Dao
interface SportDataDao {

    @Query("select * from sport_data")
    fun getSportData(): List<SportData>

    @Query("select * from sport_data where id =:id")
    fun getSportDataById(id: Int): SportData

    @Update
    fun updateAll(sportData: List<SportData>)

    @Update
    fun updateSportData(sportData: SportData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(sportData: List<SportData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSportData(sportData: SportData)

    @Delete
    fun deleteAll(sportData: List<SportData>)

    @Delete
    fun deleteSportData(sportData: SportData)
}