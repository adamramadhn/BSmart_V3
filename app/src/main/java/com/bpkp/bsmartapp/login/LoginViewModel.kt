package com.bpkp.bsmartapp.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bpkp.bsmartapp.AuthListener
import com.bpkp.bsmartapp.core.data.source.remote.network.ApiService
import com.bpkp.bsmartapp.core.data.source.remote.response.AuthLoginResponse
import com.bpkp.bsmartapp.core.data.source.remote.response.AuthUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class LoginViewModel: ViewModel() {
    var authListener: AuthListener? = null

    fun onLoginButtonClick(user_email: String, user_password: String) {
        authListener?.onStarted()
        if (user_email.isEmpty() || user_password.isEmpty()) {
            authListener?.onFailure("Invalid email or password")
            return
        }
        val loginResponse = userLogin(user_email, user_password)
        authListener?.onSuccess(loginResponse)
    }


    fun userLogin(user_email: String, user_password: String): LiveData<AuthUserResponse> {
        val loginResponse = MutableLiveData<AuthUserResponse>()
        try {
            ApiService().authLogin(user_email, user_password).enqueue(object :
                Callback<AuthLoginResponse> {
                override fun onResponse(
                    call: Call<AuthLoginResponse>,
                    response: Response<AuthLoginResponse>
                ) {
                    if(response.isSuccessful){
                        if (response.body()?.dataUser.isNullOrEmpty()){
                            authListener?.onFailure("Invalid username or password!")
                        }else{
                            loginResponse.value = response.body()?.dataUser?.get(0)
                        }
                    }else{
                        authListener?.onFailure("Something wrong..${response.errorBody().toString()}")
                    }
                }

                override fun onFailure(call: Call<AuthLoginResponse>, t: Throwable) {
                    authListener?.onFailure(t.toString())
                }

            })
        } catch (e: Exception){
//            Log.d("Login",e.toString())
        }
        return loginResponse
    }
}