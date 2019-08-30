package com.example.lyybbc.retrofitdemo.network

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.util.Log
import okhttp3.ResponseBody
import retrofit2.Callback
import retrofit2.Response

interface IRequestFunction<T>{
    fun onSuccess(t:T)
    fun onFailed(error: Throwable)
}

interface IOnLifeFunction<T> {
    fun onSuccess(statusCode:Int, response:Response<T>)
    fun onFailed(statusCode:Int, error: Throwable)
}

interface IOnLifeCallback<T> :IOnLifeCall {
    fun enqueue(iOnLifeFunction: IOnLifeFunction<T>)
    fun clone(): IOnLifeCallback<T>
    fun isCanceled(): Boolean
    fun cancel()

    override fun onLifeCycleResume() {
        Log.e("LifeCycle", "OnResume")
    }

    override fun onLifeCycleStop() {
        Log.e("LifeCycle", "OnStop")
    }

    override fun onLifeCycleDestroy() {
        Log.e("LifeCycle", "OnDestroy")
    }
}

interface IOnLifeCall : LifecycleObserver{
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onLifeCycleResume()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onLifeCycleStop()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onLifeCycleDestroy()

}