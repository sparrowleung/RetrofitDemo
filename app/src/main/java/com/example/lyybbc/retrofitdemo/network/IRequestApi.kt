package com.example.lyybbc.retrofitdemo.network

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface IRequestApi {

    @GET("/")
    fun getApiServiceTest(): Observable<Response<ResponseBody>>

    @GET("/")
    fun getApiServiceTestOnLife(): IOnLifeFunction<Response<ResponseBody>>
}