package com.sport.utilities

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * 线程池
 */
object SportExecutors {

    //本地磁盘
    val diskIO = Executors.newSingleThreadExecutor()

    //网络
    val networkIO = Executors.newFixedThreadPool(3)

    //主
    val mainThread = MainThreadExecutor()

    class MainThreadExecutor : Executor {
        val handler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable?) {
            handler.post(command)
        }
    }

}