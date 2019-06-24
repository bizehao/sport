package com.sport.model.cppData

/**
 * User: bizehao
 * Date: 2019-05-28
 * Time: 上午9:18
 * Description:
 */
data class TestCppData(
    val `object`: Object,
    val answer: Answer,
    val happy: Boolean,
    val list: List<Int>,
    val name: String,
    val new: New,
    val nothing: Any,
    val pi: Double,
    val size: Int
)

data class New(
    val key: Key
)

data class Key(
    val value: List<String>
)

data class Answer(
    val everything: Int
)

data class Object(
    val currency: String,
    val value: Double
)