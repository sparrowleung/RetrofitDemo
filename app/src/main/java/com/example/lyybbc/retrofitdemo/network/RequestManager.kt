package com.example.lyybbc.retrofitdemo.network

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RequestManager {

    companion object {
        fun <T:Any> onRequest(observable: Observable<T>, iRequestFunction: IRequestFunction<T>) {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(RequestObserver(iRequestFunction))
        }

        fun <T:Any> onRequest(IOnLifeFunction:IOnLifeFunction<T>, iRequestFunction: IRequestFunction<T>) {

        }
    }

    class RequestObserver<T:Any>(
            var iRequestFunction: IRequestFunction<T>
    ): Observer<T> {
        private var mDisposable: Disposable? = null
        override fun onComplete() {
            mDisposable?.dispose()
        }

        override fun onSubscribe(d: Disposable) {
            mDisposable = d
        }

        override fun onNext(t: T) {
            iRequestFunction.onSuccess(t)
        }

        override fun onError(e: Throwable) {
            iRequestFunction.onFailed(e)
        }
    }
}