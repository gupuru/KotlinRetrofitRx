package gupuru.kotlinretrofitsample.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import gupuru.kotlinretrofitsample.R
import gupuru.kotlinretrofitsample.api.client
import gupuru.kotlinretrofitsample.extention.onNext
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onResume() {
        super.onResume()

//        client().search("Future Nova")
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : Subscriber<SearchResponse>() {
//
//                    override fun onCompleted() {
//                        Log.d("ここ", "終了")
//                    }
//
//                    override fun onError(e: Throwable?) {
//                        Log.d("ここ", "えらー" + e)
//                    }
//
//                    override fun onNext(t: SearchResponse?) {
//                        Log.d("ここ", t.toString())
//                        activity_main.text.setText(t.toString())
//                    }
//
//                })

        client().search("Future Nova")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onNext {
                    Log.d("ここ", it.toString())
                    activity_main.text.setText(it.tracks.toString())
                }
                .onError {
                    Log.d("ここ", "えらー" + it)

                }.subscribe()

    }

    override fun onStop() {
        super.onStop()
    }

}