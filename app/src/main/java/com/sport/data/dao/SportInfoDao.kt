package com.sport.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sport.data.table.SportInfo
import java.util.*

/**
 * User: bizehao
 * Date: 2019-03-13
 * Time: 上午9:24
 * Description:
 */
@Dao
interface SportInfoDao {

    @Query("SELECT * FROM sport_info")
    fun getSportInfos():List<SportInfo>

    @Query("SELECT * FROM sport_info WHERE time = :time")
    fun getSportInfoByTime(time:Date):SportInfo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSportInfo(sportInfo: SportInfo)
}