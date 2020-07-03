package com.example.emergencybutton.activity.maps

import android.Manifest
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.emergencybutton.R
import com.example.emergencybutton.model.EmergencyItem
import com.example.emergencybutton.network.BaseApiService
import com.example.emergencybutton.network.UtilsApi
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class MapsActivity : AppCompatActivity(), MapsConstruct.View, OnMapReadyCallback {

        private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
        private lateinit var mMap: GoogleMap
        private lateinit var locationCallback: LocationCallback
        private lateinit var locationRequest: LocationRequest
        private var mApiService: BaseApiService = UtilsApi.getAPIService()!!
        private var compositeDisposable: CompositeDisposable = CompositeDisposable()
        private var presenter: MapsPresenter = MapsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        showDexterPermission()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled
    }

    override fun showDexterPermission() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {

                        buildLocationRequest()
                        buildLocationCallback()

                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this@MapsActivity)

                        //start update location
                        fusedLocationProviderClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
                    }
                }

                override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest?>?, token: PermissionToken?) {
                    Toast.makeText(this@MapsActivity, "permission denied", Toast.LENGTH_SHORT).show()
                }
            }).check()
    }

    override fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f

        Log.d("request", "kepanggil")
    }

    override fun buildLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                // Add a marker in my location and move the camera
                val myLocation = LatLng(locationResult.lastLocation.latitude, locationResult.lastLocation.longitude)
                mMap.addMarker(MarkerOptions().position(myLocation).title("my location"))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17.0f))
//                presenter.getAllEmergencies(locationResult.lastLocation)
                getAllEmergencies(myLocation)
                Log.d("callback", "kepanggil")
            }
        }
    }

    fun getAllEmergencies(lastLocation: LatLng){
        compositeDisposable.add(
            mApiService.getAllEmergencies(lastLocation.latitude.toString(), lastLocation.longitude.toString())
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

    override fun onError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

//    override fun onPause() {
//        if (locationCallback != null) {
//            fusedLocationProviderClient.removeLocationUpdates(locationCallback)
//        }
//        super.onPause()
//    }
//
//    override fun onResume() {
//        if (locationCallback != null) {
//            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
//        }
//        super.onResume()
//    }
//
//    override fun onStop() {
//        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
//        compositeDisposable.clear()
//        super.onStop()
//    }
}
