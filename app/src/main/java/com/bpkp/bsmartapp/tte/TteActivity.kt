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
                passphrase = tteBinding.etPasphrase.text?.trim().toString()
                Log.d("ZZZ", "$idSt , $nik , $passphrase")
                ApiService().getTte(idSt, nik, passphrase).enqueue(object : Callback<DetailST> {
                    override fun onResponse(call: Call<DetailST>, response: Response<DetailST>) {

                        if (response.isSuccessful) {
                            try {
                                x = response.body()?.message.toString()
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.setDataAndType(
                                    Uri.parse("http://10.10.20.154/api/surattugas/pdf?idst=${idSt}&pdf=true&token=b91dc65721c83b94cf5683b1afea84ba8225a7e98d85e2a6e34d8c9868995e41"),
                                    "application/pdf"
                                )
                                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
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
                                "Error Code:${response.code()}",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d(
                                "ZZZ",
                                "Error Code:${response.code()}\n Message: ${response.message()}"
                            )
                        }

                    }

                    override fun onFailure(call: Call<DetailST>, t: Throwable) {
                        Toast.makeText(this@TteActivity, "Error: $t", Toast.LENGTH_SHORT).show()
                        Log.d("ZZZ", "Error: $t")
                    }

                })
            }
        }
    }

}