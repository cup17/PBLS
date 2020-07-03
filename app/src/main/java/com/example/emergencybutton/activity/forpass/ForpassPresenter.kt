package com.example.emergencybutton.activity.forpass

import com.example.emergencybutton.model.ForPassItem
import com.example.emergencybutton.network.BaseApiService
import com.example.emergencybutton.network.UtilsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForpassPresenter(var view : ForpassConstruct.View) : ForpassConstruct.Presenter{

    var mApiService: BaseApiService = UtilsApi.getAPIService()!!

    override fun getDataPass(email: String) {
        mApiService.forpassRequest(email)
            .enqueue(object : Callback<ForPassItem>{
                override fun onFailure(call: Call<ForPassItem>, t: Throwable) {
                    view.onFailure(t.localizedMessage)
                    view.onFailure(t.message.toString())
                }

                override fun onResponse(call: Call<ForPassItem>, response: Response<ForPassItem>) {
                    view.onSuccess(response.body()?.msg.toString())
                    view.onSuccess(response.message())
                    response.body()?.data?.pass?.let { view.showPass(it) }
                }
            })
    }
}