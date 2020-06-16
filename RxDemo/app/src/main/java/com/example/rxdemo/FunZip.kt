package com.example.rxdemo

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

object FunZip {
    fun first(): ObservableSource<String> {
        return Observable.create<String> { emitter: ObservableEmitter<String> ->
            Thread.sleep(2000)
            Log.e("mytest", "   A 发送 11 ")
            emitter.onNext("11")
            Thread.sleep(2000)
            Log.e("mytest", "   A 发送 12 ")
            emitter.onNext("12")
            Thread.sleep(2000)
            Log.e("mytest", "   A 发送 13 ")
            emitter.onNext("13")
            emitter.onComplete()
        }.subscribeOn(Schedulers.io())
    }

    fun second(): ObservableSource<String> {
        return Observable.create<String> { emitter ->
            Thread.sleep(3000)
            Log.e("mytest", "   B 发送 21 ")
            emitter.onNext("21")
            Thread.sleep(2000)
            Log.e("mytest", "   B 发送 22 ")
            emitter.onNext("22")
            Thread.sleep(3000)
            Log.e("mytest", "   B 发送 23 ")
            emitter.onNext("23")
        }.subscribeOn(Schedulers.io())
    }

    fun zipper(): BiFunction<String, String, String>? {
        return BiFunction { s1: String, s2: String -> "$s1-$s2" }
    }


    fun zip() {
        Observable.zip<String, String, String>(
            first(),
            second(),
            zipper()
        ).observeOn(Schedulers.io())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {
//                    disposable = d
                }

                override fun onNext(s: String) {
                    Log.e(
                        "mytest",
                        "   收到 onNext " + Thread.currentThread()
                            .name + " zip result  " + s
                    )
                    if (s == "12-22") {
//                        disposable.dispose()
                    }
                }

                override fun onError(e: Throwable) {}
                override fun onComplete() {}
            })
    }

}