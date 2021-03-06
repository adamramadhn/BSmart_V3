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
import com.bpkp.bsmartapp.MainActivity.Companion.ID_PEGAWAI
import com.bpkp.bsmartapp.MainActivity.Companion.ID_RULE
import com.bpkp.bsmartapp.MainActivity.Companion.ID_RULE1
import com.bpkp.bsmartapp.MainActivity.Companion.ID_RULE2
import com.bpkp.bsmartapp.MainActivity.Companion.ID_RULE3
import com.bpkp.bsmartapp.MainActivity.Companion.ID_RULE4
import com.bpkp.bsmartapp.MainActivity.Companion.ID_RULE5
import com.bpkp.bsmartapp.MainActivity.Companion.NAMA_RULE
import com.bpkp.bsmartapp.MainActivity.Companion.NAMA_RULE1
import com.bpkp.bsmartapp.MainActivity.Companion.NAMA_RULE2
import com.bpkp.bsmartapp.MainActivity.Companion.NAMA_RULE3
import com.bpkp.bsmartapp.MainActivity.Companion.NAMA_RULE4
import com.bpkp.bsmartapp.MainActivity.Companion.NAMA_RULE5
import com.bpkp.bsmartapp.MainActivity.Companion.NIP_MAIN
import com.bpkp.bsmartapp.MainActivity.Companion.NAME_MAIN
import com.bpkp.bsmartapp.MainActivity.Companion.NIK
import com.bpkp.bsmartapp.MainActivity.Companion.USERID_MAIN
import com.bpkp.bsmartapp.R
import com.bpkp.bsmartapp.core.data.source.remote.response.AuthLoginResponse
import com.bpkp.bsmartapp.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel

//dipakai
class LoginActivity : AppCompatActivity(), View.OnClickListener, AuthListener {

    private lateinit var loginBinding: ActivityLoginBinding
    private var userEmail: String = ""
    private var userPassword: String = ""
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

    override fun onSuccess(loginResponse: LiveData<AuthLoginResponse>) {
        loginResponse.observe(this, {
            progress_bar.visibility = View.GONE
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(NAME_MAIN, it.data_user[0].nama)
            intent.putExtra(ESELON_MAIN, it.data_user[0].eselon)
            intent.putExtra(NIP_MAIN, it.data_user[0].nip)
            intent.putExtra(USERID_MAIN, it.data_user[0].id)
            intent.putExtra(NIK, it.data_user[0].NIK)


            intent.putExtra(NAMA_RULE,it.data_user[0].namarule)
            intent.putExtra(ID_RULE,it.data_user[0].rule_id)
            intent.putExtra(ID_PEGAWAI,it.data_user[0].id_pegawai)
            //new
            //Array -> 0 = Admin, 1 = Manager, 2 = Admin Unit, 3 = Pengelola Keuangan Unit, 4 = Pegawai
            intent.putExtra(NAMA_RULE1,it.rule[0].namarule)
            intent.putExtra(ID_RULE1,it.rule[0].new_id_rule)

            intent.putExtra(NAMA_RULE2,it.rule[1].namarule)
            intent.putExtra(ID_RULE2,it.rule[1].new_id_rule)

            intent.putExtra(NAMA_RULE3,it.rule[2].namarule)
            intent.putExtra(ID_RULE3,it.rule[2].new_id_rule)

            intent.putExtra(NAMA_RULE4,it.rule[3].namarule)
            intent.putExtra(ID_RULE4,it.rule[3].new_id_rule)

            intent.putExtra(NAMA_RULE5,it.rule[4].namarule)
            intent.putExtra(ID_RULE5,it.rule[4].new_id_rule)

            intent.putExtra(USERNAME_MAIN, prefHelper.getString(Constant.PREF_USERNAME).toString())
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        })

    }

    override fun onFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        progress_bar.visibility = View.GONE
    }

}