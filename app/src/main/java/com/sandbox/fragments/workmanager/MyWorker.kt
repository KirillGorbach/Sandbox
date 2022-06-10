package com.sandbox.fragments.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorker(val context: Context, params : WorkerParameters)
    : Worker(context, params) {
    override fun doWork(): Result {
        Log.d("<---", "doWork: start")
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.d("<---", "doWork: end")
        return Result.success()
    }
}