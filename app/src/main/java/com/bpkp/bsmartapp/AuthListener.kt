package com.bpkp.bsmartapp

import androidx.lifecycle.LiveData
import com.bpkp.bsmartapp.core.data.source.remote.response.AuthUserResponse

interface AuthListener {
    fun onStarted()
    fun onSuccess(loginResponse: LiveData<AuthUserResponse>)
    fun onFailure(message:String)
}