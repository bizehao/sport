package com.sport.data.network.api

import com.sport.model.Province
import retrofit2.Call
import retrofit2.http.GET

/**
 * User: bizehao
 * Date: 2019-03-15
 * Time: 上午9:31
 * Description:
 */
interface SportService {

    @GET("api/china")
    fun getPlan(): Call<List<Province>>
}