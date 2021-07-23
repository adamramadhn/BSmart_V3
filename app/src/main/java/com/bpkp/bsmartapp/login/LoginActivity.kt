package com.bpkp.bsmartapp.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.bpkp.bsmartapp.AuthListener
import com.bpkp.bsmartapp.MainActivity
import com.bpkp.bsmartapp.MainActivity.Companion.USERNAME_MAIN
import com.bpkp.bsmartapp.MainActivity.Companion.ESELON_MAIN
import com.bpkp.bsmartapp.MainActivity.Companion.NIP_MAIN
import com.bpkp.bsmartapp.MainActivity.Companion.NAME_MAIN
import com.bpkp.bsmartapp.MainActivity.Companion.USERID_MAIN
import com.bpkp.bsmartapp.R
import com.bpkp.bsmartapp.core.data.source.remote.response.AuthUserResponse
import com.bpkp.bsmartapp.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity(), View.OnClickListener, AuthListener {

    private lateinit var loginBinding: ActivityLoginBinding
    var userEmail: String = ""
    var userPassword: String = ""
    private val loginViewModel: LoginViewModel by viewModel()
    lateinit var prefHelper: PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        loginViewModel.authListener = this
        btn_sign_in.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        prefHelper = PrefHelper(this)
        when (v?.id) {
            R.id.btn_sign_in -> {
                userEmail = loginBinding.etEmail.text?.trim().toString()
                userPassword = loginBinding.etPassword.text?.trim().toString()
                loginViewModel.onLoginButtonClick(userEmail, userPassword)
                if (userEmail.isNotEmpty() && userPassword.isNotEmpty()) {
                    prefHelper.put(Constant.PREF_USERNAME, et_email.text.toString())
                    prefHelper.put(Constant.PREF_PASSWORD, et_password.text.toString())
                    prefHelper.put(Constant.PREF_IS_LOGIN, true)
                }
            }
        }
    }

    override fun onStarted() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        prefHelper = PrefHelper(this)
        //CHECK WHERE USER IS LOGGED OR NOT
        if (prefHelper.getBoolean(Constant.PREF_IS_LOGIN)) {
            //NAVIGATE
            loginViewModel.onLoginButtonClick(
                prefHelper.getString(Constant.PREF_USERNAME).toString().trim(),
                prefHelper.getString(Constant.PREF_PASSWORD).toString().trim()
            )
        }
    }

    override fun onSuccess(loginResponse: LiveData<AuthUserResponse>) {
        loginResponse.observe(this, {
            Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
            progress_bar.visibility = View.GONE

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(NAME_MAIN, it.nama)
            intent.putExtra(ESELON_MAIN, it.eselon)
            intent.putExtra(NIP_MAIN, it.nip)
            intent.putExtra(USERID_MAIN, it.id)
            intent.putExtra(USERNAME_MAIN, prefHelper.getString(Constant.PREF_USERNAME).toString())
            startActivity(intent)

        })

    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        progress_bar.visibility = View.GONE
    }

}