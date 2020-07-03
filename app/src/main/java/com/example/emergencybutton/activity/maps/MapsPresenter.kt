package com.example.emergencybutton.activity.maps

import android.location.Location
import android.util.Log
import com.example.emergencybutton.R
import com.example.emergencybutton.model.EmergencyItem
import com.example.emergencybutton.network.BaseApiService
import com.example.emergencybutton.network.UtilsApi
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.internal.IGoogleMapDelegate
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class MapsPresenter : MapsConstruct.Presenter {

    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    var mApiService: BaseApiService = UtilsApi.getAPIService()!!
    private lateinit var googleMapDelegate: IGoogleMapDelegate
    companion object{
        lateinit var mMap: GoogleMap
    }

    override fun getAllEmergencies(lastLocation: Location) {
        mMap = GoogleMap(googleMapDelegate)
        compositeDisposable.add(mApiService.getAllEmergencies(lastLocation.latitude.toString(), lastLocation.longitude.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object : Consumer<List<EmergencyItem>> {
                override fun accept(t: List<EmergencyItem>?) {
                    for (emergencyItem: EmergencyItem in t!!) run {
                        val emergencyLocation = LatLng(emergencyItem.lat, emergencyItem.lng)

                        mMap.addMarker(
                            MarkerOptions()
                                .position(emergencyLocation)
                                .title(emergencyItem.name)
                                .snippet(StringBuilder("Distance : "). append(emergencyItem.distanceInKm).append(" km").toString()))
                            .setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_emergency_location))
                    }
                }
            },
                Consumer<Throwable> { t -> Log.d("error : ", t.localizedMessage) }))
    }
}