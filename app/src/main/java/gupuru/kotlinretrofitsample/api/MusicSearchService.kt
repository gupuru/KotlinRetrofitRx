package gupuru.kotlinretrofitsample.api

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import gupuru.kotlinretrofitsample.entity.response.SearchResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable


fun client(): MusicSearchService {
    val okHttpClient: OkHttpClient = OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build()

    val builder = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.spotify.com")
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(createGson()))
            .build()

    return builder.create(MusicSearchService::class.java)
}

fun createGson(): Gson {
    return GsonBuilder().create()
}

interface MusicSearchService {
    @GET("/v1/search?type=track&market=JP")
    fun search(@Query("q") name: String): Observable<SearchResponse>
}
