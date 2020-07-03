package com.example.emergencybutton.activity.login

import android.util.Log
import com.example.emergencybutton.activity.forpass.LoginConstruct
import com.example.emergencybutton.model.UserItem
import com.example.emergencybutton.network.BaseApiService
import com.example.emergencybutton.network.UtilsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginPresenter(val view: LoginConstruct.View) : LoginConstruct.Presenter {

    override fun pushLoginData(email: String, pass: String) {
        var mApiService: BaseApiService = UtilsApi.getAPIService()!!

        mApiService.loginRequest(email, pass)
            .enqueue(object : Callback<UserItem> {
                override fun onFailure(call: Call<UserItem>, t: Throwable?) {
                    Log.d("failure", t.toString())
                    view.isFailure(t.toString())
                }

                override fun onResponse(call: Call<UserItem>, response: Response<UserItem>) {
                    if (response.body()?.status.equals("true")) {
                        Log.d("success", response.message())
                        view.isSuccess(response.message())
                        view.saveUserData(response)
                        view.goToHome()
                    } else {
                        Log.d("error : ", response.message())
                        view.isFailure("Berhasil menonaktifkan lokasi")
                    }
                }
            })
    }
}