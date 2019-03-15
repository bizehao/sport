package com.sport.model

import com.google.gson.annotations.SerializedName

/**
 * 省类
 */
data class Province(@SerializedName("name") val provinceName: String, @SerializedName("id") val provinceCode: Int) {
    @Transient
    val id = 0
}