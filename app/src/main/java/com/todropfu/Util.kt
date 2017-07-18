package com.todropfu

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory

/**
 * Created by dudco on 2017-07-16.
 */

fun resourceInSmapleSizeImg(resources: Resources, id:Int, sampleSize: Int): Bitmap {
    val opt = BitmapFactory.Options()
    opt.inSampleSize = sampleSize
    return BitmapFactory.decodeResource(resources, id, opt)
}
