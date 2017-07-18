package com.todropfu

import android.app.ProgressDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import org.jetbrains.anko.AnkoLogger

/**
 * Created by dudco on 2017-07-16.
 */

class Util: AnkoLogger{

    companion object {
        val network = NetworkUtil()

        fun resourceInSmapleSizeImg(resources: Resources, id:Int, sampleSize: Int): Bitmap {
            val opt = BitmapFactory.Options()
            opt.inSampleSize = sampleSize
            return BitmapFactory.decodeResource(resources, id, opt)
        }


        private var mProgressDialog: ProgressDialog? = null

        fun showProgressDialog(mContext: Context, content: String = "로그인중...") {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog(mContext)
                mProgressDialog!!.apply {
                    setProgressStyle(ProgressDialog.STYLE_SPINNER)
                    setMessage(content)
                }
            }
            mProgressDialog!!.show()
        }

        fun hideProgressDialog() {
            if (mProgressDialog != null && mProgressDialog!!.isShowing) {
                mProgressDialog!!.dismiss()
            }
        }

        inline fun <reified T> getPref(context: Context, key: String, defValue: T): T? {
            val pref = context.getSharedPreferences("ToDropFu", MODE_PRIVATE)
            when (T::class.java) {
                String::class.java -> return pref.getString(key, defValue as String) as T
                Int::class.java -> return pref.getInt(key, defValue as Int) as T
                Boolean::class.javaObjectType -> return pref.getBoolean(key, defValue as Boolean) as T
                Float::class.java -> return pref.getFloat(key, defValue as Float) as T
                Long::class.java -> return pref.getLong(key, defValue as Long) as T
            }
            return null
        }

        inline fun <reified T> setPref(context: Context, key: String, value: T) {
            Log.d("dudco", "called setPref")
            Log.d("dudco", T::class.java.toString())
            val pref = context.getSharedPreferences("ToDropFu", MODE_PRIVATE)
            val editor = pref.edit()
            when (T::class.java) {
                String::class.java -> editor.putString(key, value as String)
                Int::class.java -> editor.putInt(key, value as Int)
                Boolean::class.javaObjectType -> editor.putBoolean(key, value as Boolean)
                Float::class.java -> editor.putFloat(key, value as Float)
                Long::class.java -> editor.putLong(key, value as Long)
            }
            editor.apply()
        }

    }

}
