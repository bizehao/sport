package com.sport.data.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import java.util.*

/**
 * User: bizehao
 * Date: 2019-03-13
 * Time: 上午9:03
 * Description: 运动信息表
 */
@Entity(tableName = "sportInfos")
data class SportInfo(
    @PrimaryKey @ColumnInfo(name = "id")
    val infoId:String, //信息id
    val time:Date, //时间
    val duration: Long, //时长
    val number: Int, //俯卧撑数量
    val state: Int //运动后的状态
) {

}