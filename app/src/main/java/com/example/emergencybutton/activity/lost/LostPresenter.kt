package com.example.emergencybutton.activity.lost

import com.example.emergencybutton.model.LostItem
import com.example.emergencybutton.network.BaseApiService
import com.example.emergencybutton.network.UtilsApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class LostPresenter(var view : LostConstruct.View) : LostConstruct.Presenter{

    private var mApiService: BaseApiService = UtilsApi.getAPIService()!!

    override fun pushDataLost(
        roleType: RequestBody,
        name: RequestBody,
        postersName: RequestBody,
        number: RequestBody,
        date: RequestBody,
        location: RequestBody,
        description: RequestBody,
        image: MultipartBody.Part
    ) {
        mApiService.insertLostThingsRequest(roleType, name, postersName, number, date, location, description, image)
            .enqueue(object : Callback<LostItem> {
                override fun onFailure(call: Call<LostItem>, t: Throwable) {
                    view.onFailure(t.localizedMessage)
                    view.onFailure(t.message.toString())
                }

                override fun onResponse(
                    call: Call<LostItem>,
                    response: Response<LostItem>
                ) {
                    view.onSuccess(response.body()?.msg.toString())
                    view.onSuccess(response.message())
                }
            })
    }
}