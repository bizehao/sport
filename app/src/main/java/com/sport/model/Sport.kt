package com.sport.model

/**
 * User: bizehao
 * Date: 2019-03-22
 * Time: 上午11:23
 * Description: 顶部每组的个数
 */
data class GroupSport(
    val id:Int,
    val total:Int,
    val groupNum:Int,
    val iterators:ArrayList<IteratorSport>
)

data class IteratorSport(
    val id:Int, //id
    val num:Int, //每组的个数
    var current:Boolean //是否是正在做的哪一组
)

/**
 * 俯卧撑列表recyclerview的数据
 */
data class Sport(
    val id: Int, //id
    var isStartOfGroup:Boolean,//是否是每组的开端
    val grade: Int,//等级
    val intervalTime: Int, //间隔时间
    var current:Boolean //是否是正在做的哪一组
){
    lateinit var pushUpNum: String  //俯卧撑数量
    var dateTime: String = ""//日期
}