package com.example.emergencybutton.activity.register

import com.example.emergencybutton.activity.forpass.RegisterConstruct
import com.example.emergencybutton.model.UserItem
import com.example.emergencybutton.network.BaseApiService
import com.example.emergencybutton.network.UtilsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterPresenter(val view: RegisterConstruct.View) : RegisterConstruct.Presenter {
    override fun pushRegisterData(nama: String, noHP: String, email: String, pass: String) {

        var mApiService: BaseApiService = UtilsApi.getAPIService()!!

        mApiService.registerRequest(nama, noHP, email, pass)
            .enqueue(object : Callback<UserItem>{
                override fun onFailure(call: Call<UserItem>, t: Throwable) {
                    view.isFailure(t.localizedMessage)
                }

                override fun onResponse(call: Call<UserItem>, response: Response<UserItem>) {
                    response.body()?.data?.errorMsg?.let { view.isSuccess(it) }
                    view.goToLogin()
                }

            })

    }
}