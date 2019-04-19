package com.sport.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sport.data.table.Person

/**
 * User: bizehao
 * Date: 2019-03-12
 * Time: 下午2:10
 * Description:
 */
@Dao
interface PersonDao {

    @Query("SELECT * FROM persons ORDER BY name")
    fun getPersons(): List<Person>

    @Query("SELECT * FROM persons WHERE name= :name")
    fun getPersonByName(name: String): Person

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(persons: List<Person>)
}