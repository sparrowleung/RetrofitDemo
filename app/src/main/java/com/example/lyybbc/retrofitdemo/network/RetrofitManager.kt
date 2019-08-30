package com.example.lyybbc.retrofitdemo.network

import android.app.Activity
import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class  RetrofitManager {

    companion object {
        private var retrofitManager: RetrofitManager? = null
        fun getInstance() : RetrofitManager {
            synchronized(this) {
                if (retrofitManager == null){
                    retrofitManager =  RetrofitManager()
                }
            }

            return retrofitManager!!
        }
    }

    private fun onOkHttpSetting() : OkHttpClient {
        val interceptor = Interceptor {
            it.proceed(it.request().newBuilder().build())
        }

        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .build()
    }


    fun <T: Any> getApiServiceOnRx(baseUrl: String, clazz: Class<T>) : T {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(onOkHttpSetting())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(clazz)
    }

    fun <T: Any> getApiServiceOnLife(baseUrl: String, activity: Activity, clazz: Class<T>) : T {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(onOkHttpSetting())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(OnLifeCallAdapterFactory(activity))
                .build()
                .create(clazz)
    }
}