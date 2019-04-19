package com.sport.data.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * User: bizehao
 * Date: 2019-03-12
 * Time: 下午1:57
 * Description: 人员数据表
 */
@Entity(tableName = "persons")
data class Person(

    @PrimaryKey @ColumnInfo(name = "id")
    val personId: String,
    val name: String,
    val age: Int,
    val birthday: Date,
    val height: Double,
    val weight: Double,
    val imageUrl: String = ""


) {

    override fun toString(): String = name;
}