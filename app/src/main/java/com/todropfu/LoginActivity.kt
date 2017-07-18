package com.todropfu

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setBg(2)

        btnLogin.onClick {
            startActivity<MainActivity>()
        }
        textReg.onClick {
            startActivity<RegisterActivity>()
        }
    }

    fun setBg(sampleSize: Int){
        imgBg.setImageBitmap(resourceInSmapleSizeImg(resources, R.drawable.bg_login, sampleSize))
    }
}