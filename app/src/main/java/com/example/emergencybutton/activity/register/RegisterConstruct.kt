package com.example.emergencybutton.activity.forpass

interface RegisterConstruct {
    interface View{
        fun goToLogin()
        fun isFailure(msg : String)
        fun isSuccess(msg : String)
    }

    interface Presenter{
        fun pushRegisterData(nama : String, noHP : String, email : String, pass : String)
    }
}