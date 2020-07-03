package com.example.emergencybutton.activity.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.emergencybutton.R
import com.example.emergencybutton.activity.forpass.RegisterConstruct
import com.example.emergencybutton.activity.login.LoginActivity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterConstruct.View {

    private val presenter: RegisterPresenter = RegisterPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register.setOnClickListener {
            presenter.pushRegisterData(edt_nama.text.toString(), edt_nomor.text.toString(), edt_regis_email.text.toString(), edt_regis_pass.text.toString())
            goToLogin()
        }

        imb_regis_back.setOnClickListener {
            goToLogin()
        }

    }

    override fun goToLogin() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun isFailure(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun isSuccess(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
