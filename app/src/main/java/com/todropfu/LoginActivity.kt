package com.todropfu

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import java.util.*
import kotlin.properties.Delegates

class LoginActivity : AppCompatActivity(), AnkoLogger {

    var callbackManager by Delegates.notNull<CallbackManager>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        info{Util.getPref(this@LoginActivity, "init", false)}
        Util.getPref(this@LoginActivity, "init", false)?.let{
            if(it){
                startActivity<MainActivity>()
                finish()
            }
        }

        setBg(2)

        btnLogin.onClick {
            startActivity<MainActivity>()
        }
        textReg.onClick {
            startActivity<RegisterActivity>()
        }

        FacebookSdk.setApplicationId("154408675125421")
        FacebookSdk.sdkInitialize(applicationContext)

        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager, object: FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                info { result!!.accessToken.token }
                startActivity<RegisterActivity>("token" to result!!.accessToken.token)
                finish()
            }

            override fun onCancel() {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onError(error: FacebookException?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })

        btnLoginFB.onClick {
            LoginManager.getInstance().logInWithReadPermissions(this@LoginActivity, Arrays.asList("public_profile"))
        }
    }

    fun setBg(sampleSize: Int){
        imgBg.setImageBitmap(Util.resourceInSmapleSizeImg(resources, R.drawable.bg_login, sampleSize))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }
}