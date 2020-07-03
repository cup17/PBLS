package com.example.emergencybutton.activity.forpass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.emergencybutton.R
import com.example.emergencybutton.activity.login.LoginActivity
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_forpass.*

class ForpassActivity : AppCompatActivity(), ForpassConstruct.View {

    private val presenter: ForpassPresenter = ForpassPresenter(this)

    //buat variable
    lateinit var email : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forpass)

        email = edt_email_forpass.text.toString().trim()

        btn_submit.setOnClickListener {
            if (email == "" || email.isEmpty()) {
                presenter.getDataPass(edt_email_forpass.text.toString())
            } else {
                edt_email_forpass.error = "Isi kolom email terlebih dahulu!"
           }
        }

        imb_forpass_back.setOnClickListener {
            goToLogin()
        }
    }

    override fun onSuccess(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun onFailure(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun showPass(pass: String) {
        Toasty.normal(this, "password nya adalah : $pass", Toasty.LENGTH_LONG).show()
    }

    override fun goToLogin() {
        val intent = Intent(this@ForpassActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}
