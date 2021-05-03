package com.example.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_login.setOnClickListener {
            val pref = getSharedPreferences("login", Context.MODE_PRIVATE)
            if (pref.contains("id") && pref.contains("email") && pref.contains("pass")) {
                if ((pref.getString("id", "") == text_id.text.toString() || pref.getString("email","") == text_id.text.toString())
                    && pref.getString("pass","") == text_pass.text.toString()) {
                    startActivity(Intent(this, LogoutActivity::class.java))
                } else { Snackbar.make(layout_main_, "Login failed: not matching id or password", Snackbar.LENGTH_LONG).show() }
            }
        }
        button_signup.setOnClickListener { startActivity(Intent(this, VerifyEmailActivity::class.java).apply { putExtra("isFirst", true) }) }
        button_find.setOnClickListener { startActivity(Intent(this, VerifyEmailActivity::class.java).apply { putExtra("isFirst", false) }) }
    }
}
