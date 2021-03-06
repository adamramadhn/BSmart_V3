package com.bpkp.bsmartapp.tte

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bpkp.bsmartapp.R
import com.bpkp.bsmartapp.core.data.source.remote.network.ApiService
import com.bpkp.bsmartapp.core.data.source.remote.response.DetailST
import com.bpkp.bsmartapp.databinding.ActivityTteBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//dipakai
class TteActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val ID_ST = "ID_ST"
        const val NIK_TTE = "NIK_TTE"
    }

    private lateinit var tteBinding: ActivityTteBinding
    private var idSt = 0
    private var nik = ""
    private var passphrase: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tteBinding = ActivityTteBinding.inflate(layoutInflater)
        setContentView(tteBinding.root)
        tteBinding.btnCancel.setOnClickListener(this)
        tteBinding.btnLoginTte.setOnClickListener(this)
        idSt = intent.getIntExtra(ID_ST, 0)
        nik = intent.getStringExtra(NIK_TTE).toString()

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_cancel -> {
                finish()
            }
            R.id.btn_login_tte -> {
                var x = ""
//                var y = "3175020706740008"
                passphrase = tteBinding.etPasphrase.text?.trim().toString()
                ApiService().getTte(idSt, passphrase, nik).enqueue(object : Callback<DetailST> {
                    override fun onResponse(call: Call<DetailST>, response: Response<DetailST>) {

                        if (response.isSuccessful) {
                            try {
                                x = response.body()?.message.toString()
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.setDataAndType(
                                    Uri.parse("http://aplikasistore.org/api/surattugas/pdf?id_st=${idSt}&pdf=true&token=b91dc65721c83b94cf5683b1afea84ba8225a7e98d85e2a6e34d8c9868995e41"),
                                    "application/pdf"
                                )
                                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(Intent.createChooser(intent, "Open with..."))
                            } catch (e: Exception) {
                                Toast.makeText(
                                    this@TteActivity,
                                    "Error: $e\nMessage: $x",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                                Log.d(
                                    "ZZZ",
                                    "Error Code:${response.code()}\n Message: ${response.message()}"
                                )
                            }
                        } else {
                            Toast.makeText(
                                this@TteActivity,
                                "Maaf passphrase yang anda masukkan salah!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }

                    override fun onFailure(call: Call<DetailST>, t: Throwable) {
                        Toast.makeText(this@TteActivity, "Error: $t", Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }
    }

}