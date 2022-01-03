package com.bpkp.bsmartapp.SPDetail

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bpkp.bsmartapp.R
import com.bpkp.bsmartapp.core.data.source.remote.network.ApiService
import com.bpkp.bsmartapp.core.data.source.remote.response.sp.ListSpResponse
import com.bpkp.bsmartapp.core.data.source.remote.response.sp.SpResponse
import com.bpkp.bsmartapp.databinding.ActivityDetailSuratPengantarBinding
import com.bpkp.bsmartapp.detail.DetailSuratTugasActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//dipakai
class SPDetail : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailSuratPengantarBinding
    private lateinit var SPViewModel: SPViewModel
    private var userName: String? = ""
    private var userEselon: String? = ""
    private var idST: Int = 0
    private var idSP: Int = 0
    private var dibuatOleh: String = ""

    companion object {
        const val USERNAMESP = "USERNAMESP"
        const val ESELONSP = "ESELONSP"
        const val IDST = "IDST"
        const val CREATEDBY = "CREATEDBY"
        const val DATA_SP = "DATA_SP"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSuratPengantarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userName = intent.getStringExtra(USERNAMESP)
        userEselon = intent.getStringExtra(ESELONSP)
        dibuatOleh = intent.getStringExtra(CREATEDBY).toString()
        idST = intent.getIntExtra(IDST, 0)
        SPViewModel = SPViewModel()

        binding.btnBack.setOnClickListener(this)
        binding.btnSuratPengantar.setOnClickListener(this)
        binding.etNote.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val imm: InputMethodManager =
                    applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etNote.windowToken, 0)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
//        getSp(idST)

//        binding.btnSetuju.setOnClickListener(this)
//        binding.btnTolak.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        val detailSp = intent.getParcelableExtra<ListSpResponse>(DATA_SP)
        showDetailTourism(detailSp)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_surat_pengantar -> {
                Toast.makeText(this, "Coming soon..", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_back -> {
                finish()
            }
//            R.id.btn_setuju -> {
//                Toast.makeText(this, "Coming soon..", Toast.LENGTH_SHORT).show()
//            }
//            R.id.btn_tolak -> {
//                Toast.makeText(this, "Coming soon..", Toast.LENGTH_SHORT).show()
//            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDetailTourism(detailSuratPengantar: ListSpResponse?) {
        detailSuratPengantar?.let {
            with(binding) {
                tvSpId.text = "${detailSuratPengantar.id_sp} |"
                idSP = detailSuratPengantar.id_sp
                idST = detailSuratPengantar.st_id
                tvDate.text = detailSuratPengantar.tgl_sp
                tvSpNumber.text = detailSuratPengantar.no_sp.toString()
                tvDescription.text = detailSuratPengantar.hal
                yth.text = detailSuratPengantar.yth
                createdBy.text = dibuatOleh

                var note1 = detailSuratPengantar.review_note_es1
                var note2 = detailSuratPengantar.review_note_es2
                var note3 = detailSuratPengantar.review_note_es3
                var note4 = detailSuratPengantar.review_note_es4
                note1 = when (note1) {
                    null -> {
                        ""
                    }
                    else -> {
                        "Eselon 1: ${detailSuratPengantar.review_note_es1}\n"
                    }
                }
                note2 = when (note2) {
                    null -> {
                        ""
                    }
                    else -> {
                        "Eselon 2: ${detailSuratPengantar.review_note_es2}\n"
                    }
                }
                note3 = when (note3) {
                    null -> {
                        ""
                    }
                    else -> {
                        "Eselon 3: ${detailSuratPengantar.review_note_es3}\n"
                    }
                }
                note4 = when (note4) {
                    null -> {
                        ""
                    }
                    else -> {
                        "Eselon 4: ${detailSuratPengantar.review_note_es4}\n"
                    }
                }
                etNote.hint = "$note1$note2$note3$note4"

                when (userEselon) {
                    "ESELON IV-A", "ESELON IV-B" -> {
                        if (detailSuratPengantar.apv_es2 == 1 || detailSuratPengantar.apv_es2 == 0 || detailSuratPengantar.apv_es1 == 1 || detailSuratPengantar.apv_es1 == 0 || detailSuratPengantar.apv_es3 == 1 || detailSuratPengantar.apv_es3 == 0) {
                            btnCancel.visibility = View.GONE
                            btnTolak.visibility = View.GONE
                            btnSetuju.visibility = View.GONE
                        } else {
                            if (detailSuratPengantar.apv_es4 != 2 && detailSuratPengantar.approve_id_user_eselon_4 == DetailSuratTugasActivity.USERID_DETAIL) {
                                btnCancel.visibility = View.VISIBLE
                                btnTolak.visibility = View.GONE
                                btnSetuju.visibility = View.GONE
                            } else if (detailSuratPengantar.apv_es4 == 1 || detailSuratPengantar.apv_es4 == 0) {
                                btnCancel.visibility = View.GONE
                                btnTolak.visibility = View.GONE
                                btnSetuju.visibility = View.GONE
                            } else {
                                btnCancel.visibility = View.GONE
                                btnTolak.visibility = View.VISIBLE
                                btnSetuju.visibility = View.VISIBLE
                            }
                        }
                    }
                    "ESELON III-A", "ESELON III-B" -> {
                        if (detailSuratPengantar.apv_es4 == 0 || detailSuratPengantar.apv_es2 == 1 || detailSuratPengantar.apv_es2 == 0 || detailSuratPengantar.apv_es1 == 1 || detailSuratPengantar.apv_es1 == 0) {
                            btnCancel.visibility = View.GONE
                            btnTolak.visibility = View.GONE
                            btnSetuju.visibility = View.GONE
                        } else {
                            if (detailSuratPengantar.apv_es3 != 2 && detailSuratPengantar.approve_id_user_eselon_3 == DetailSuratTugasActivity.USERID_DETAIL) {
                                btnCancel.visibility = View.VISIBLE
                                btnTolak.visibility = View.GONE
                                btnSetuju.visibility = View.GONE
                            } else if (detailSuratPengantar.apv_es3 == 1 || detailSuratPengantar.apv_es3 == 0) {
                                btnCancel.visibility = View.GONE
                                btnTolak.visibility = View.GONE
                                btnSetuju.visibility = View.GONE
                            } else {
                                btnCancel.visibility = View.GONE
                                btnTolak.visibility = View.VISIBLE
                                btnSetuju.visibility = View.VISIBLE
                            }
                        }
                    }
                    "ESELON II-A", "ESELON II-B" -> {
                        if (detailSuratPengantar.apv_es3 == 0 || detailSuratPengantar.apv_es4 == 0 || detailSuratPengantar.apv_es1 == 1 || detailSuratPengantar.apv_es1 == 0) {
                            btnCancel.visibility = View.GONE
                            btnTolak.visibility = View.GONE
                            btnSetuju.visibility = View.GONE
                        } else {
                            if (detailSuratPengantar.apv_es2 != 2 && detailSuratPengantar.approve_id_user_eselon_2 == DetailSuratTugasActivity.USERID_DETAIL) {
                                btnCancel.visibility = View.VISIBLE
                                btnTolak.visibility = View.GONE
                                btnSetuju.visibility = View.GONE
                            } else if (detailSuratPengantar.apv_es2 == 1 || detailSuratPengantar.apv_es2 == 0) {
                                btnCancel.visibility = View.GONE
                                btnTolak.visibility = View.GONE
                                btnSetuju.visibility = View.GONE
                            } else {
                                btnCancel.visibility = View.GONE
                                btnTolak.visibility = View.VISIBLE
                                btnSetuju.visibility = View.VISIBLE
                            }
                        }
                    }
                    "ESELON I-A", "ESELON I-B" -> {
                        if (detailSuratPengantar.apv_es2 == 0 || detailSuratPengantar.apv_es4 == 0 || detailSuratPengantar.apv_es3 == 0) {
                            btnCancel.visibility = View.GONE
                            btnTolak.visibility = View.GONE
                            btnSetuju.visibility = View.GONE
                        } else {
                            if (detailSuratPengantar.apv_es1 != 2 && detailSuratPengantar.approve_id_user_eselon_1 == DetailSuratTugasActivity.USERID_DETAIL) {
                                btnCancel.visibility = View.VISIBLE
                                btnTolak.visibility = View.GONE
                                btnSetuju.visibility = View.GONE
                            } else {
                                btnCancel.visibility = View.GONE
                                btnTolak.visibility = View.VISIBLE
                                btnSetuju.visibility = View.VISIBLE
                            }
                        }
                    }
                    else -> {
                        btnCancel.visibility = View.GONE
                        btnTolak.visibility = View.GONE
                        btnSetuju.visibility = View.GONE
                        btnTte.visibility = View.GONE
                    }
                }

                when (detailSuratPengantar.apv_es4) {
                    1 -> {
                        circleYellowEs4.visibility = View.VISIBLE
                        circleRedEs4.visibility = View.GONE
                    }
                    0 -> {
                        circleRedEs4.visibility = View.VISIBLE
                        circleYellowEs4.visibility = View.GONE
                    }
                    else -> {
                        circleGreyEs4.visibility = View.VISIBLE
                        circleRedEs4.visibility = View.GONE
                        circleYellowEs4.visibility = View.GONE
                    }
                }

                when (detailSuratPengantar.apv_es3) {
                    1 -> {
                        circleYellowEs3.visibility = View.VISIBLE
                        circleRedEs3.visibility = View.GONE
                        lineYellowEs3.visibility = View.VISIBLE
                    }
                    0 -> {
                        circleRedEs3.visibility = View.VISIBLE
                        circleYellowEs3.visibility = View.GONE
                        lineYellowEs3.visibility = View.VISIBLE
                    }
                    else -> {
                        circleGreyEs3.visibility = View.VISIBLE
                        circleRedEs3.visibility = View.GONE
                        circleYellowEs3.visibility = View.GONE
                        lineYellowEs3.visibility = View.GONE
                    }
                }

                when (detailSuratPengantar.apv_es2) {
                    1 -> {
                        circleYellowEs2.visibility = View.VISIBLE
                        circleRedEs2.visibility = View.GONE
                        lineYellowEs2.visibility = View.VISIBLE
                        lineYellowEs3.visibility = View.VISIBLE
                    }
                    0 -> {
                        circleRedEs2.visibility = View.VISIBLE
                        circleYellowEs2.visibility = View.GONE
                        lineYellowEs2.visibility = View.VISIBLE
                        lineYellowEs3.visibility = View.VISIBLE
                    }
                    else -> {
                        circleGreyEs2.visibility = View.VISIBLE
                        circleRedEs2.visibility = View.GONE
                        circleYellowEs2.visibility = View.GONE
                        lineYellowEs2.visibility = View.GONE
                    }
                }

                when (detailSuratPengantar.apv_es1) {
                    1 -> {
                        circleYellowEs1.visibility = View.VISIBLE
                        circleRedEs1.visibility = View.GONE
                        lineYellowEs2.visibility = View.VISIBLE
                        lineYellowEs3.visibility = View.VISIBLE
                        lineYellowEs1.visibility = View.VISIBLE
                    }
                    0 -> {
                        circleRedEs1.visibility = View.VISIBLE
                        circleYellowEs1.visibility = View.GONE
                        lineYellowEs1.visibility = View.VISIBLE
                        lineYellowEs2.visibility = View.VISIBLE
                        lineYellowEs3.visibility = View.VISIBLE
                    }
                    else -> {
                        circleGreyEs1.visibility = View.VISIBLE
                        circleRedEs1.visibility = View.GONE
                        circleYellowEs1.visibility = View.GONE
                        lineYellowEs1.visibility = View.GONE
                    }
                }
            }

            binding.btnSetuju.setOnClickListener {
                val mAlertDialog = AlertDialog.Builder(this)
                mAlertDialog.setTitle("Perhatian")
                mAlertDialog.setMessage("Apakah Anda yakin menyetujui surat tugas ini?")
                mAlertDialog.setIcon(R.drawable.ic_warning)

                mAlertDialog.setPositiveButton("Ya") { _, _ ->
                    Handler(mainLooper).postDelayed({
                        SPViewModel.setSp(idSP)
                        SPViewModel.getSp().observe(this, {
                            showDetailTourism(it?.get(0))
                            binding.etNote.setText("")
                        })
                    }, 1000)
                    try {
                        SPViewModel.suratPengantar(
                            userName,
                            idST,
                            1,
                            binding.etNote.text.toString()
                        )

                    } catch (e: Exception) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                    }

                }
                mAlertDialog.setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                mAlertDialog.show()
            }

            binding.btnTolak.setOnClickListener {
                val mAlertDialog = AlertDialog.Builder(this)
                mAlertDialog.setTitle("Perhatian")
                mAlertDialog.setMessage("Apakah Anda yakin menolak surat tugas ini?")
                mAlertDialog.setIcon(R.drawable.ic_warning)
                mAlertDialog.setPositiveButton("Ya") { _, _ ->
                    Handler(mainLooper).postDelayed({
                        SPViewModel.setSp(idSP)
                        SPViewModel.getSp().observe(this, {
                            showDetailTourism(it?.get(0))
                            binding.etNote.setText("")
                        })
                    }, 1000)
                    try {
                        SPViewModel.suratPengantar(
                            userName,
                            idST,
                            0,
                            binding.etNote.text.toString()
                        )
                    } catch (e: Exception) {
                        Toast.makeText(this, "Error: $e,", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                mAlertDialog.setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                mAlertDialog.show()
            }

            binding.btnCancel.setOnClickListener {
                val mAlertDialog = AlertDialog.Builder(this)
                mAlertDialog.setTitle("Perhatian")
                mAlertDialog.setMessage("Apakah Anda yakin membatalkan tanggapan surat tugas ini?")
                mAlertDialog.setIcon(R.drawable.ic_warning)
                mAlertDialog.setPositiveButton("Ya") { _, _ ->
                    Handler(mainLooper).postDelayed({
                        SPViewModel.setSp(idSP)
                        SPViewModel.getSp().observe(this, {
                            showDetailTourism(it?.get(0))
                            binding.etNote.setText("")
                        })
                    }, 1000)
                    try {
                        SPViewModel.suratPengantar(
                            userName,
                            idST,
                            2,
                            ""
                        )
                    } catch (e: Exception) {
                        Toast.makeText(this, "Error: $e,", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                mAlertDialog.setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                mAlertDialog.show()
            }
        }
    }

    private fun getSp(idST: Int) {

        ApiService().getSP(idST).enqueue(object : Callback<SpResponse> {
            override fun onResponse(call: Call<SpResponse>, response: Response<SpResponse>) {
                try {
                    if (response.isSuccessful) {
                        val listResponse = response.body()?.SuratPengantar?.get(0)
                        showDetailTourism(listResponse)
                    }
                } catch (e: java.lang.Exception) {
                    Toast.makeText(this@SPDetail, "Data tidak ditemukan!,", Toast.LENGTH_LONG)
                        .show()
                    finish()
//                    binding.progressBar.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<SpResponse>, t: Throwable) {
                Toast.makeText(this@SPDetail, "Error: $t,", Toast.LENGTH_LONG)
                    .show()
            }

        })
    }
}