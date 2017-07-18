package com.todropfu

import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

/**
 * Created by dudco on 2017. 7. 18..
 */

class NetworkUtil {

    init{
        FuelManager.instance.basePath = "http://kafuuchino.one:3000"
    }

    fun fbLogin(token: String,address: String, callback: (request: Request, response: Response, result: Result<Json, FuelError>)->Unit){
        "/auth/facebook/token".httpGet(listOf("access_token" to token, "address" to address )).responseJson {
            request, response, result -> callback(request, response, result)
        }
    }
}