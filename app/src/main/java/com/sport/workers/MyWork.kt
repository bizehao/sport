package com.sport.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * User: bizehao
 * Date: 2019-03-14
 * Time: 上午11:59
 * Description:
 */
class MyWork(var context:Context, workerParams:WorkerParameters):Worker(context,workerParams) {
    override fun doWork(): Result {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        sayA<String>()
        return Result.success()
    }

    fun <T> sayA(){

    }
}