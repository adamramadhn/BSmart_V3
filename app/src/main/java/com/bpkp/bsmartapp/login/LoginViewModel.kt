package com.bpkp.bsmartapp.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bpkp.bsmartapp.AuthListener
import com.bpkp.bsmartapp.core.data.source.remote.network.ApiService
import com.bpkp.bsmartapp.core.data.source.remote.response.AuthLoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Exception

class LoginViewModel: ViewModel() {
    var authListener: AuthListener? = null

    fun onLoginButtonClick(user_email: String, user_password: String) {
        authListener?.onStarted()
        if (user_email.isEmpty() || user_password.isEmpty() || user_password.length<2) {
            authListener?.onFailure("Invalid email or password")
            return
        }
        val loginResponse = userLogin(user_email, user_password)
        authListener?.onSuccess(loginResponse)
    }


    private fun userLogin(user_email: String, user_password: String): LiveData<AuthLoginResponse> {
        val loginResponse = MutableLiveData<AuthLoginResponse>()
        try {
            ApiService().authLogin(user_email, user_password).enqueue(object :
                Callback<AuthLoginResponse> {
                override fun onResponse(
                    call: Call<AuthLoginResponse>,
                    response: Response<AuthLoginResponse>
                ) {
                    if(response.isSuccessful){
                        if (response.body()?.data_user.isNullOrEmpty()){
                            authListener?.onFailure("Invalid username or password!")
                        }else{
                            loginResponse.value = response.body()
                        }
                    }else{
                        authListener?.onFailure("Invalid username or password!")
                    }
                }

                override fun onFailure(call: Call<AuthLoginResponse>, t: Throwable) {
                    authListener?.onFailure(t.toString())
                }

            })
        } catch (e: Exception){
            authListener?.onFailure("Something wrong..")
        }
        return loginResponse
    }
}