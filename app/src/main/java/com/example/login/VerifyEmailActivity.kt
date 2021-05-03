package com.example.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_verify_email.*
import java.util.*
import kotlin.concurrent.schedule


class VerifyEmailActivity : AppCompatActivity() {
    private val code by lazy { GregorianCalendar.getInstance().timeInMillis }
    private var emaild = false
    private var count = 180

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_email)
        val isFirst = intent.getBooleanExtra("isFirst", true)
        layout_code.isEnabled = false
        button_send.setOnClickListener {
            FindActivity.sendEmail("이메일 인증 코드", "인증 코드는 $code 입니다", text_email.text.toString())
            emaild = true; count = 180; layout_code.isEnabled = true; button_send.text = "인증하기"
            Timer().schedule(delay = 0, period = 1000) {
                if (--count == 0) { layout_code.hint = "시간 초과!"; emaild = false; layout_code.isEnabled = false; button_send.text = "다시 인증하기" }
                else { layout_code.hint = "${count / 60}:${ count % 60 }" }
            }
        }
        text_code.addTextChangedListener(SignupActivity.generateTextWatcher {
            if(it?.toString() == code.toString()) { button_verify.isEnabled = true }
            else { layout_code.error = "인증 코드가 틀립니다" }
        })
    }

}
