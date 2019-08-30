package com.example.lyybbc.retrofitdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.lyybbc.retrofitdemo.network.IRequestApi
import com.example.lyybbc.retrofitdemo.network.IRequestFunction
import com.example.lyybbc.retrofitdemo.network.RequestManager
import com.example.lyybbc.retrofitdemo.network.RetrofitManager
import okhttp3.ResponseBody
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val baseUrl: String = "https://api.github.com/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.request).setOnClickListener {
            RequestManager.onRequest(RetrofitManager.getInstance().getApiServiceOnRx(baseUrl, IRequestApi::class.java).getApiServiceTest(), addRequestFunction())
        }

        findViewById<Button>(R.id.requestLife).setOnClickListener {
            RequestManager.onRequest(RetrofitManager.getInstance().getApiServiceOnLife(baseUrl, this@MainActivity, IRequestApi::class.java).getApiServiceTestOnLife(), addRequestFunction())
        }
    }


    private fun <T:Any> addRequestFunction() : IRequestFunction<T> {
        return object :IRequestFunction<T> {
            override fun onSuccess(t: T) {
                val response = t as Response<ResponseBody>
                Log.e("MainActivity", "Response Success -> ${response.code()} , ${response.body()?.string()}")
            }

            override fun onFailed(error: Throwable) {
                Log.e("MainActivity", "Response Failed -> ${error.message}")
            }
        }
    }
}
