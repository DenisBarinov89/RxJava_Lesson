package com.example.rxjava_lesson

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class MainActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Свой вариант Observable
        val dispose = Observable.just(
            "Начало",
            "Алексей",
            "Дмитрий",
            "Иван",
            "Иван",
            "Георгий",
            "Василий",
            "Конец"
        )
            .subscribeOn(Schedulers.newThread())
            .skip(1)
            .skipLast(1)
            .distinct()
            .filter { it.lowercase().contains("е") }
            .map { "$it Васильевич" }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Log.d("result", "$it") },
                {},
                {}
            )

        //Свой вариант Flowable
        val disposeSecond = Flowable.just(1, 2, 3, 4, 5, 6, 1)
            .distinct()
            .delay(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.newThread())
            .flatMap {
                val delay = java.util.Random().nextInt(5)
                Flowable.just(it).delay(delay.toLong(), TimeUnit.SECONDS)
            }
            .scan { t1, t2 -> t1 * t2 }
            .buffer(2)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { Log.d("result", "$it") }


        }
    }

