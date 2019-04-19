package com.sport.data.table

import androidx.room.*

/**
 * User: bizehao
 * Date: 2019-04-03
 * Time: 下午1:29
 * Description:
 */
@Entity(tableName = "sport_data")
data class SportData(
    @PrimaryKey @ColumnInfo(name = "id")
    val id: Int, //id
    val grade: Int,//等级
    @ColumnInfo(name = "interval_time")
    val intervalTime: Int, //间隔时间 s

    @ColumnInfo(name = "sub_data")
    val subData: List<SubData>,

    @ColumnInfo(name = "date_time")
    var dateTime: String = "" //时间
) {
    data class SubData(
        val id: Int, //id
        val pushUpNum: Int  //俯卧撑数量
    )
}