package com.example.emergencybutton.fragment.notification

import android.util.Log
import com.example.emergencybutton.model.ResponseNotification
import com.example.emergencybutton.network.BaseApiService
import com.example.emergencybutton.network.UtilsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationPresenter(val view : NotificationConstruct.View) : NotificationConstruct.Presenter {
    override fun getNotificationData() {
        var mApiService: BaseApiService = UtilsApi.getAPIService()!!

        mApiService.getAllLostThings()
            .enqueue(object : Callback<ResponseNotification> {
                override fun onFailure(call: Call<ResponseNotification>?, t: Throwable?) {
                    Log.d("failure", t.toString())
                    view.onFailure(t.toString())
                }

                override fun onResponse(call: Call<ResponseNotification>?, response: Response<ResponseNotification>?) {
                    Log.d("success", response?.body().toString())
                    view.onSuccess("success", response?.body().toString())
                    view.showData(response?.body()?.data)
                }

            })
    }
}