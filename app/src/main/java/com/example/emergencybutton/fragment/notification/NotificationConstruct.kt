package com.example.emergencybutton.fragment.notification

import com.example.emergencybutton.model.NotificationItem

interface NotificationConstruct {
    interface View {
        fun onFailure(msg: String)
        fun onSuccess(msg: String, toString: String)
        fun showData(data: List<NotificationItem?>?)
        fun goToMaps()
    }

    interface Presenter {
        fun getNotificationData()
    }
}