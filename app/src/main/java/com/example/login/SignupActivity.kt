package com.example.login

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.edit
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.*
import kotlin.concurrent.schedule

class SignupActivity : AppCompatActivity() {
    var isEmailEmpty = true
    var isIDEmpty = true
    var isPassEmpty = true
    var isCheckEmpty = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        text_email.addTextChangedListener(generateTextWatcher {
            isEmailEmpty = it?.toString() == "" || it?.toString() == null
            layout_email.error = if (isEmailEmpty) "필수 항목을 입력하세요" else null
            button3.isEnabled = !isCheckEmpty && !isEmailEmpty && !isIDEmpty && !isPassEmpty
        })

        text_id.addTextChangedListener(generateTextWatcher {
            isIDEmpty = it?.toString() == "" || it?.toString() == null; layout_id.error = if (isIDEmpty) "필수 항목을 입력하세요" else null
            button3.isEnabled = !isCheckEmpty && !isEmailEmpty && !isIDEmpty && !isPassEmpty
        })

        text_pass.addTextChangedListener(generateTextWatcher {
            isPassEmpty = it?.toString() == "" || it?.toString() == null; layout_pass.error = if (isPassEmpty) "필수 항목을 입력하세요" else null
            button3.isEnabled = !isCheckEmpty && !isEmailEmpty && !isIDEmpty && !isPassEmpty
        })

        text_check.addTextChangedListener(generateTextWatcher {
            isCheckEmpty = it?.toString() == "" || it?. toString() == null || it.toString() != text_pass.text?.toString()
            layout_check.error = if (isCheckEmpty) "필수 항목을 입력하세요" else if (it?.toString() != text_pass.text?.toString()) "비밀번호와 똑같이 입력하세요" else null
            button3.isEnabled = !isCheckEmpty && !isEmailEmpty && !isIDEmpty && !isPassEmpty
        })

        button3.setOnClickListener {
            val pref = getSharedPreferences("login", Context.MODE_PRIVATE)
            pref.edit(commit = true) {
                putString("email", text_email.text.toString())
                putString("id", text_id.text.toString())
                putString("pass", text_pass.text.toString())
            }
            Snackbar.make(layout_signup, "등록했습니다. 이제 로그인하세요", Snackbar.LENGTH_LONG).show()
            Timer().schedule(delay= 2750) { finish() }
        }
    }
    companion object {
        fun generateTextWatcher(afterTextChanged: (Editable?) -> Unit) =
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) = afterTextChanged(s)

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            }
    }
}
