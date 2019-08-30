package com.example.lyybbc.retrofitdemo.network

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleRegistry
import android.content.Context
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class OnLifeCallAdapter<R>(
        var responseType: Type,
        var lifecycleRegistry: LifecycleRegistry? = null,
        var isCancelable:Boolean = true
):CallAdapter<R, IOnLifeCallback<R>>{
    override fun adapt(call: Call<R>): IOnLifeCallback<R> {
        return OnLifeCallbackImpl(call, isCancelable).apply {
            if (lifecycleRegistry?.currentState != Lifecycle.Event.ON_PAUSE ||
                    lifecycleRegistry?.currentState != Lifecycle.Event.ON_STOP ||
                    lifecycleRegistry?.currentState != Lifecycle.Event.ON_DESTROY) {
                lifecycleRegistry?.addObserver(this)
            }
        }
    }

    override fun responseType(): Type {
        return responseType
    }
}