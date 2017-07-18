package com.todropfu

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setBg(2)
        btnToBack.onClick {
            finish()
        }
        btnToNext.onClick {
            startActivity<AfterRegisterActivity>()
        }
    }

    fun setBg(sampleSize: Int){
        regContainer.background = BitmapDrawable(resources, resourceInSmapleSizeImg(resources, R.drawable.bg_signup, sampleSize))
    }
}
