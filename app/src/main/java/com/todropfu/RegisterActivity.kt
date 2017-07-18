package com.todropfu

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class RegisterActivity : AppCompatActivity(),AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val token = intent.getStringExtra("token")
        setBg(2)
        btnToBack.onClick {
            finish()
        }
        btnToNext.onClick {
            Util.showProgressDialog(this@RegisterActivity)
            Util.network.fbLogin(token, reg_editAdrress.text.toString(), {
                request, response, result ->
                Util.hideProgressDialog()
                info{ request }
                info{ response}
                info { result }

                val json = result.get().obj()
                Util.setPref(this@RegisterActivity, "_id", json.get("_id").toString())
                Util.setPref(this@RegisterActivity, "address", json.get("address").toString())
                Util.setPref(this@RegisterActivity, "bucket", json.get("bucket").toString())
                Util.setPref(this@RegisterActivity, "name", json.get("name").toString())
                Util.setPref(this@RegisterActivity, "init", true)
            })
            Util.network.connectBucket(Util.getPref(this@RegisterActivity, "_id", "")!!, {
                _, _, result -> Util.setPref(this@RegisterActivity, "bucket_id", result.get().obj().get("_id").toString())
            })
            startActivity<AfterRegisterActivity>()
            finish()
        }


    }

    fun setBg(sampleSize: Int){
        regContainer.background = BitmapDrawable(resources, Util.resourceInSmapleSizeImg(resources, R.drawable.bg_signup, sampleSize))
    }
}
