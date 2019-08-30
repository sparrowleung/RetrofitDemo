package com.example.lyybbc.retrofitdemo.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OnLifeCallbackImpl<T>(
        var callImpl: Call<T>,
        var isCancelable: Boolean
):IOnLifeCallback<T>{
    override fun enqueue(iOnLifeFunction: IOnLifeFunction<T>) {
        callImpl.enqueue(object :Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                iOnLifeFunction.onFailed(0, t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                iOnLifeFunction.onSuccess(response.code(), response)
            }
        })
    }

    override fun clone(): IOnLifeCallback<T> {
        return OnLifeCallbackImpl(callImpl.clone(),isCancelable)
    }

    override fun isCanceled(): Boolean {
        return isCancelable
    }

    override fun cancel() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}