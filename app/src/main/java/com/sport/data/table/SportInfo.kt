package com.sport.data.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import java.util.*

/**
 * User: bizehao
 * Date: 2019-03-13
 * Time: 上午9:03
 * Description: 运动信息表
 */
@Entity(
    tableName = "sport_Info",
    foreignKeys = [ForeignKey(entity = SportData::class, parentColumns = ["id"], childColumns = ["sport_data_id"])]
)
data class SportInfo(
    @PrimaryKey
    val time: Date, //时间
    @ColumnInfo(name = "total_duration")
    val totalDuration: Long, //总时长 秒
    @ColumnInfo(name = "sport_data_id")
    val sportDataId: Int, //对应的SportDataId
    @ColumnInfo(name = "sub_info")
    val subInfo: List<SubInfo> //运动后的状态
) {
    data class SubInfo(
        val id: Int, //id
        val pushUpNum: Int,  //俯卧撑数量
        val duration: Long //时长 秒
    )
}