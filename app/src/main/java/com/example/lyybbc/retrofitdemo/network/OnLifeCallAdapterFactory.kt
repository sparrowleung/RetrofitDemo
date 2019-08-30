package com.example.lyybbc.retrofitdemo.network

import android.app.Activity
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class OnLifeCallAdapterFactory(
        private val activity: Activity,
        private val isCancelable: Boolean
) : CallAdapter.Factory(){
    override fun get(returnType: Type?, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (returnType == null) {
            return  null
        }

        if (returnType !is ParameterizedType) {
            throw IllegalArgumentException("ReturnType is Error")
        }

        val responseType = getParameterUpperBound(0, returnType)
        if (activity is LifecycleOwner && activity.lifecycle is LifecycleRegistry) {
            return OnLifeCallAdapter<Any>(responseType, activity.lifecycle as LifecycleRegistry, isCancelable)
        }

        return OnLifeCallAdapter<Any>(responseType, (activity as LifecycleOwner).lifecycle as LifecycleRegistry)
    }


}