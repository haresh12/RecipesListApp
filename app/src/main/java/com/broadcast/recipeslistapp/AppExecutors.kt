package com.broadcast.recipeslistapp

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class AppExecutors {

    private val mNetworkIO = Executors.newScheduledThreadPool(3)

    fun networkIO(): ScheduledExecutorService {
        return mNetworkIO
    }

}