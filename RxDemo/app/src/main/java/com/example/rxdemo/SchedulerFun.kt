package com.example.rxdemo

import android.util.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

object SchedulerFun {
    fun main(){
        Observable.create<String> { emitter ->

            Thread.sleep(1000)
            Log.e("mytest", "   B 发送 21 ")
            emitter.onNext("21")
            Thread({
                Log.e("mytest", "  野线程 发送 22 ")

                emitter.onNext("野线程 发送 22")
                Thread.sleep(5000)}

            ).start()

            Log.e("mytest", "   B 发送 23 ")
            emitter.onNext("22")
            Thread.sleep(3000)
            Log.e("mytest", "   B 发送 24 ")
            emitter.onNext("23")
        }.subscribeOn(Schedulers.io()).observeOn(Schedulers.trampoline()).subscribe {
                resp->
            Log.e("mytest", "   onNext  "+Thread.currentThread().name)
        }

    }
}
