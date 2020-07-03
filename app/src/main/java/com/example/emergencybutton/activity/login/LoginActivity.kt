package com.example.emergencybutton.activity.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.emergencybutton.R
import com.example.emergencybutton.activity.MainActivity
import com.example.emergencybutton.activity.forpass.ForpassActivity
import com.example.emergencybutton.activity.forpass.LoginConstruct
import com.example.emergencybutton.activity.register.RegisterActivity
import com.example.emergencybutton.model.UserItem
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Response


class LoginActivity : AppCompatActivity(), LoginConstruct.View {

    private val presenter: LoginPresenter = LoginPresenter(this)
    lateinit var myPref: SharedPreferences
    lateinit var loginPref: SharedPreferences
    lateinit var loginEditor: SharedPreferences.Editor
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

         myPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE)
         loginPref = getSharedPreferences("login", Context.MODE_PRIVATE)
         editor = getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit()
         loginEditor = getSharedPreferences("login", Context.MODE_PRIVATE).edit()

        checkLogin()

        btn_login.setOnClickListener {
            presenter.pushLoginData(edt_email.text.toString(), edt_pass.text.toString())
            finish()
        }

        btn_go_to_regis.setOnClickListener {
            goToRegister()
        }

        btn_forpass.setOnClickListener {
            goToForpass()
        }
    }

    override fun goToHome() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun goToRegister() {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

    override fun goToForpass() {
        val intent = Intent(this@LoginActivity, ForpassActivity::class.java)
        startActivity(intent)
    }

    override fun saveUserData(data: Response<UserItem>?) {
        loginEditor.putString("isLogin", "true")
        editor.putString("id", data?.body()?.data?.id.toString())
        editor.putString("nama", data?.body()?.data?.name)
        editor.putString("nomor", data?.body()?.data?.number)
        editor.putString("email", data?.body()?.data?.email)
        editor.putString("pass", data?.body()?.data?.pass)
        if (data?.body()?.data?.image != null) {
            editor.putString("image", data?.body()?.data?.image)
        } else {
            editor.putString("image", "null")
        }
        loginEditor.apply()
        editor.apply()
    }

    override fun isFailure(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun isSuccess(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun checkLogin() {
        var isLogin = loginPref.getString("isLogin", "")
        if (isLogin.equals("true")) {
            goToHome()
        } else {
            Toast.makeText(this, "tak login laah", Toast.LENGTH_LONG)
        }
    }
}
