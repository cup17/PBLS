package com.example.emergencybutton.fragment.home

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.bumptech.glide.Glide
import com.example.emergencybutton.R
import com.example.emergencybutton.model.EmergencyItem
import com.example.emergencybutton.model.UserItem
import com.example.emergencybutton.network.BaseApiService
import com.example.emergencybutton.network.UtilsApi
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.internal.IGoogleMapDelegate
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class HomePresenter(val view: HomeConstruct.View) : HomeConstruct.Presenter {

    var mApiService: BaseApiService = UtilsApi.getAPIService()!!

//    lateinit var googleMapDelegate: IGoogleMapDelegate
//    private  var mMap: GoogleMap = GoogleMap(googleMapDelegate)
//    private lateinit var emergencyItem: EmergencyItem

    override fun pushEmergencyON(name: String, lat: String, lng: String) {
        mApiService.insertEmergencyRequest(name, lat, lng)
            .enqueue(object : Callback<EmergencyItem>{
                override fun onFailure(call: Call<EmergencyItem>, t: Throwable) {
                    view.onFailure(t.localizedMessage)
                    view.onFailure(t.message.toString())
                }

                override fun onResponse(call: Call<EmergencyItem>, response: Response<EmergencyItem>) {
                    view.onSuccess(response.message())
                    view.onSuccess(response.body()!!.msg)
//                    var emergencyLocation = LatLng(response.body()!!.lat, response.body()!!.lng)
//
//                    mMap.addMarker(
//                        MarkerOptions()
//                            .position(emergencyLocation)
//                            .title(emergencyItem.name)
//                            .snippet(StringBuilder("Distance : "). append(emergencyItem.distanceInKm). append(" km").toString()))
//                        .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_emergency_location))
                }
            })
    }

    override fun pushEmergencyOFF(name: String) {
        mApiService.deleteEmergencyRequest(name)
            .enqueue(object : Callback<EmergencyItem>{
                override fun onFailure(call: Call<EmergencyItem>, t: Throwable) {
                    view.onFailure(t.localizedMessage)
                    view.onFailure(t.message.toString())
                }

                override fun onResponse(
                    call: Call<EmergencyItem>,
                    response: Response<EmergencyItem>
                ) {
                    view.onSuccess(response.message())
                    view.onSuccess(response.body()?.msg.toString())
                }

            })
    }
}