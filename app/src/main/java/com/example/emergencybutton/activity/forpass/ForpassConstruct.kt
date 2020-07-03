package com.example.emergencybutton.activity.forpass

interface ForpassConstruct {
    interface View{
        fun onSuccess(msg: String)
        fun onFailure(msg: String)
        fun showPass(pass: String)
        fun goToLogin()
    }

    interface Presenter{
        fun getDataPass(email: String)
    }
}