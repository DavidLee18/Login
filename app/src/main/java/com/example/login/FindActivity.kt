package com.example.login

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_find.*
import java.util.*
import javax.activation.DataHandler
import javax.activation.FileDataSource
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

class FindActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find)

        button_send.setOnClickListener {
            try {
                sendEmail("비밀번호 재설정", "비밀번호 재설정은 구글신께 물어보세요!! \n\n <a href=\"https://google.com\">구글신 용안 뵙기</a>", text_email_.text.toString())
                Snackbar.make(layout_find, "메일 발송 성공", Snackbar.LENGTH_SHORT).show()
            }
            catch(e: Exception) { e.printStackTrace(); Snackbar.make(layout_find, "메일 발송 실패..", Snackbar.LENGTH_LONG).show() }
        }
    }

    companion object {
        fun sendEmail(title: String, body: String, dest: String) = sendEmail(title, body, dest, null, null)

        fun sendEmail(
            title: String,      // 메일 제목
            body: String,       // 메일 내용
            dest: String,       // 받는 메일 주소
            fileName: String?,   // 첨부파일 이름
            filePath: String?    // 첨부파일 경로
        )
        {
            // 보내는 메일 주소와 비밀번호
            val username = "djwodus@gmail.com"
            val password = "dlalxj18gh"

            object : AsyncTask<Unit, Unit, Unit>() {
                override fun doInBackground(vararg params: Unit) {
                    val props = Properties().apply {
                        put("mail.smtp.auth", "true")
                        put("mail.smtp.starttls.enable", "true")
                        put("mail.smtp.host", "smtp.gmail.com")
                        put("mail.smtp.port", "587")
                    }

                    // 비밀번호 인증으로 세션 생성
                    val session = Session.getInstance(props,
                        object : javax.mail.Authenticator() {
                            override fun getPasswordAuthentication(): PasswordAuthentication =
                                PasswordAuthentication(username, password)
                        })

                    // 메시지 객체 만들기
                    val message = MimeMessage(session)
                    message.setFrom(InternetAddress(username))
                    // 수신자 설정, 여러명으로도 가능
                    message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(dest)
                    )
                    message.subject = title
                    message.setText(body)

                    /*
                    // 파일을 담기 위한 Multipart 생성
                    val multipart = MimeMultipart()
                    val messageBodyPart = MimeBodyPart()
                    val source = FileDataSource(filePath)

                    messageBodyPart.dataHandler = DataHandler(source)
                    messageBodyPart.fileName = fileName
                    multipart.addBodyPart(messageBodyPart)

                    // 메시지에 파일 담고
                    message.setContent(multipart)
                    */

                    // 전송
                    Transport.send(message)
                }
            }.execute()
        }
    }
}
