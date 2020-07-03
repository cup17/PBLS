package com.example.emergencybutton.fragment.home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.emergencybutton.R
import com.example.emergencybutton.activity.found.FoundActivity
import com.example.emergencybutton.activity.lost.LostActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), HomeConstruct.View, PopupMenu.OnMenuItemClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var locationCallback: LocationCallback = LocationCallback()
    var locationRequest: LocationRequest = LocationRequest()

    lateinit var myPref: SharedPreferences

    var presenter: HomePresenter = HomePresenter(this)

//    lateinit var context: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationProviderClient = this.context?.let {
            LocationServices.getFusedLocationProviderClient(
                it
            )
        }!!

        //start update location
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())

        myPref = context!!.getSharedPreferences("userInfo", Context.MODE_PRIVATE)

        tv_nama.text = myPref.getString("nama", "")

        showProfileImage()

        tbt_emergency_button.isChecked = false

        var name = myPref.getString("nama", "").toString()

        tbt_emergency_button.setOnClickListener {
            if (!tbt_emergency_button.isChecked) {
                presenter.pushEmergencyOFF(name)
                Toast.makeText(context, "Ketekan juga kok", Toast.LENGTH_SHORT).show()
            } else {
                showDialog()
                Toast.makeText(context, "Ketekan kok", Toast.LENGTH_SHORT).show()
            }
        }

        btn_losfon.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                showDialogLosFon()
            }
        }
    }

    override fun goToLost() {
        val intent = Intent(context, LostActivity::class.java)
        startActivity(intent)
    }

    override fun goToFound() {
        val intent = Intent(context, FoundActivity::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun showDialogLosFon() {
        val popupMenu = PopupMenu(context, view, Gravity.NO_GRAVITY,R.attr.actionOverflowMenuStyle, 0)
        popupMenu.setOnMenuItemClickListener(this@HomeFragment)
        popupMenu.inflate(R.menu.losfon_item)
        popupMenu.show()
    }

    override fun onFailure(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showProfileImage() {
        var image = myPref.getString("email", "").toString()
        if (image.equals("")) {
            Toast.makeText(context, "gambar masih kosong", Toast.LENGTH_SHORT).show()
        } else {
            Picasso.get().load("http://emergency.unjgwjforindonesia.com/picture/profiles/$image.jpg").error(R.drawable.ic_error_outline_white_24dp).into(cmv_home_profile);
            Log.d("image", image)
            Log.d("harusnya keluar", image)
        }
    }

    override fun showDialog(): AlertDialog? {

        var name = myPref.getString("nama", "").toString()

        var builder = AlertDialog.Builder(context)
        builder.setMessage("Apakah anda yakin ingin memencet \"panic button\"?\n" +
                "klik ok bahwa anda menyetujui ketentuan dan persyaratan aplikasi ini!! ")
        builder.setIcon(R.drawable.ic_warning)
        builder.setPositiveButton("yes"
        ) { dialog, which ->
            Log.d("dexter", "masuk")

            presenter.pushEmergencyON(name, "-33.839939", "151.006283")

//            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//            locationRequest.interval = 5000
//            locationRequest.fastestInterval = 3000
//            locationRequest.smallestDisplacement = 10f
//
//            locationCallback = object : LocationCallback() {
//                override fun onLocationResult(locationResult: LocationResult) {
//                    super.onLocationResult(locationResult)
//
//                    Toast.makeText(context, "test location callback", Toast.LENGTH_SHORT).show()
//
//                    var latitude = locationResult.lastLocation?.latitude.toString()
//                    var longitude = locationResult.lastLocation?.longitude.toString()
//
//                    var name = myPref.getString("nama", "").toString()
//
//                    Log.d("dexter", "masuk3")
//
//                    presenter.pushEmergencyON(name, latitude, longitude)
//                }
//            }
        }

        builder.create()
        return builder.show()
    }

    override fun buildLocationRequest() {

    }

    override fun buildLocationCallback() {

    }

    override fun onPause() {
        if (locationCallback != null) { fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            super.onPause()
        }
    }

    override fun onResume() {
        if (locationCallback != null) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper()) }
        super.onResume()
    }

    override fun onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        super.onStop()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_found -> goToFound()
            R.id.item_lost -> goToLost()
        }
        return false
    }
}
