package com.example.emergencybutton.fragment.notification

import android.R.attr.path
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emergencybutton.R
import com.example.emergencybutton.activity.detail.DetailActivity
import com.example.emergencybutton.activity.maps.MapsActivity
import com.example.emergencybutton.activity.register.RegisterActivity
import com.example.emergencybutton.adapter.NotificationAdapter
import com.example.emergencybutton.model.NotificationItem
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_notification.*


/**
 * A simple [Fragment] subclass.
 */
class NotificationFragment : Fragment(), NotificationConstruct.View {

    private var presenter: NotificationPresenter = NotificationPresenter(this)
//    private lateinit var notificationAdapter: NotificationAdapter

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    lateinit var myPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myPref = context!!.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        var nama = myPref.getString("nama", "Nama pengguna")
        tv_nama_pengguna.text = nama

        fab_maps.setOnClickListener { goToMaps() }

        presenter.getNotificationData()
    }

    override fun onFailure(msg: String) {
        Toasty.success(activity!!, "Fail with $msg", Toasty.LENGTH_LONG).show()
    }

    override fun onSuccess(msg: String, toString: String) {
        Toasty.success(activity!!, "Success with $msg", Toasty.LENGTH_LONG).show()
    }

    override fun showData(data: List<NotificationItem?>?) {
        rv_notification.layoutManager = LinearLayoutManager(context)
        rv_notification.adapter = context?.let { NotificationAdapter(it, data){ it ->
            Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
            val intent = Intent(context, DetailActivity::class.java)
            val bundle = Bundle()
            bundle.putString("namaPemosting", it.postersName)
            bundle.putString("nama", it.name)
            bundle.putString("tanggal", it.date)
            bundle.putString("deskripsi", it.description)
            bundle.putString("image", it.image)
            bundle.putString("nomor", it.number)
            bundle.putString("lokasi", it.location)
            intent.putExtras(bundle)
            startActivity(intent)
            }
        }
    }

    override fun goToMaps() {
        val intent = Intent(context, MapsActivity::class.java)
        startActivity(intent)
    }

}
