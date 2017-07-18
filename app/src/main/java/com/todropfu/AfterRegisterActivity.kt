package com.todropfu

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_after_register.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class AfterRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_register)
        setBg(2)

        btnAfterRegBack.onClick {
            finish()
        }
        btnAfterRegNext.onClick {
            startActivity<MainActivity>()
        }
    }

    fun setBg(sampleSize: Int){
        afterBg.setImageBitmap(resourceInSmapleSizeImg(resources, R.drawable.bg_tuto, sampleSize))
    }
}
