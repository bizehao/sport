package com.sport.data.network

import com.sport.data.network.api.SportService
import com.sport.model.Province
import retrofit2.Callback

class SportNetwork {

    private val placeService = ServiceCreator.create(SportService::class.java)

    fun fetchProvinceList(callback: Callback<List<Province>>) = placeService.getPlan().enqueue(callback)

    companion object {

        private var network: SportNetwork? = null

        fun getInstance(): SportNetwork {
            if (network == null) {
                synchronized(SportNetwork::class.java) {
                    if (network == null) {
                        network = SportNetwork()
                    }
                }
            }
            return network!!
        }

    }

}