package com.bpkp.bsmartapp.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bpkp.bsmartapp.R
import com.bpkp.bsmartapp.core.data.source.remote.response.SuratTugasResponse
import com.bpkp.bsmartapp.databinding.ActivityDetailSuratTugasBinding
import com.bpkp.bsmartapp.tte.TteActivity
import com.bpkp.bsmartapp.tte.TteActivity.Companion.ID_ST
import com.bpkp.bsmartapp.tte.TteActivity.Companion.NIK_TTE
//dipakai
class DetailSuratTugasActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val EXTRA_DATA = "extra_data"
        const val ESELON_DETAIL = "ESELON_DETAIL"
        const val NIK_DETAIL = "NIK_DETAIL"
        var USERID_DETAIL = "USERID_DETAIL"
        var ID_RULE_DETAIL = "ID_RULE_DETAIL"
        var ID_PEGAWAI_DETAIL = "ID_PEGAWAI_DETAIL"
    }

    private lateinit var binding: ActivityDetailSuratTugasBinding
    private lateinit var detailSuratTugasViewModel: DetailSuratTugasViewModel

//    private var userName: String? = ""
    private var userEselon: String? = ""
    private var nik: String? = ""
    private var idST: Int = 0
    private var createdBy: String = ""
    private var jmlPtgs: Int = 0
    private var idPegawai: String? = ""
    private var idRule: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSuratTugasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        detailSuratTugasViewModel = DetailSuratTugasViewModel()
        val detailSuratTugas = intent.getParcelableExtra<SuratTugasResponse>(EXTRA_DATA)
        idPegawai = intent.getStringExtra(ID_PEGAWAI_DETAIL)
        idRule = intent.getIntExtra(ID_RULE_DETAIL, 0)
        showDetailTourism(detailSuratTugas)
        idST = detailSuratTugas!!.id_st
        userEselon = intent.getStringExtra(ESELON_DETAIL)
        nik = intent.getStringExtra(NIK_DETAIL)
//        userName = intent.getStringExtra(USERNAME_DETAIL)
        with(binding) {
            btnBack.setOnClickListener { finish() }
            btnSuratTugas.setOnClickListener(this@DetailSuratTugasActivity)
            btnTte.setOnClickListener(this@DetailSuratTugasActivity)
//            btnLihatRkd.setOnClickListener(this@DetailSuratTugasActivity)
            btnSuratPengantar.setOnClickListener(this@DetailSuratTugasActivity)
        }
        try {
            detailSuratTugasViewModel.setDetail(idST)
            detailSuratTugasViewModel.getDetail().observe(this, {
                showDetailTourism(it?.get(0))
                jmlPtgs = 0/*it?.get(1)?.jumlahpetugas!!*/
                binding.etNote.setText("")
            })
        } catch (e: Exception) {
            Toast.makeText(this, "Error: $e", Toast.LENGTH_SHORT).show()
        }

        binding.etNote.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val imm: InputMethodManager =
                    applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.etNote.windowToken, 0)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

    }

    @SuppressLint("SetTextI18n")
    private fun showDetailTourism(detailSuratTugas: SuratTugasResponse?) {
        detailSuratTugas?.let {
            with(binding) {
                tvStId.text = "${detailSuratTugas.id_st} |"
                idST = detailSuratTugas.id_st
                val v = detailSuratTugas.tgl_st
                val timeV = v?.dropLast(8)
                tvDate.text = timeV
                val jenisSurat = detailSuratTugas.id_jenis_surat
                tvStNumber.text = detailSuratTugas.no_st
                tvDescription.text = detailSuratTugas.perihal
                val x = detailSuratTugas.tgl1
                val time1 = x?.dropLast(8)
                tvDateDuration.text = "$time1 s.d."
                val y = detailSuratTugas.tgl2
                val time2 = y?.dropLast(8)
                tvDateDuration2.text = time2
                tvBiayaSt.text = "Rp.${detailSuratTugas.biaya},-"
                tvJumlahPetugas.text = jmlPtgs.toString()
                var note1 = detailSuratTugas.review_note_es1
                var note2 = detailSuratTugas.review_note_es2
                var note3 = detailSuratTugas.review_note_es3
                var note4 = detailSuratTugas.review_note_es4

                when (jenisSurat) {
                    1 -> {
                        btnSuratPengantar.visibility = View.VISIBLE
                    }
                    2, 3, 4 -> {
                        btnSuratPengantar.visibility = View.GONE
                    }
                }
                note1 = when (note1) {
                    null -> {
                        ""
                    }
                    else -> {
                        "Eselon 1: ${detailSuratTugas.review_note_es1}\n"
                    }
                }
                note2 = when (note2) {
                    null -> {
                        ""
                    }
                    else -> {
                        "Eselon 2: ${detailSuratTugas.review_note_es2}\n"
                    }
                }
                note3 = when (note3) {
                    null -> {
                        ""
                    }
                    else -> {
                        "Eselon 3: ${detailSuratTugas.review_note_es3}\n"
                    }
                }
                note4 = when (note4) {
                    null -> {
                        ""
                    }
                    else -> {
                        "Eselon 4: ${detailSuratTugas.review_note_es4}\n"
                    }
                }
                etNote.hint = "$note1$note2$note3$note4"
                tvUserUpdate.text = detailSuratTugas.created_by
                createdBy = detailSuratTugas.created_by.toString()
//                tvUserUpdate.text = detailSuratTugas.user_id
                when (userEselon) {
                    "ESELON IV-A", "ESELON IV-B" -> {
                        if (detailSuratTugas.apv_es2 == 1 || detailSuratTugas.apv_es2 == 0 || detailSuratTugas.apv_es1 == 1 || detailSuratTugas.apv_es1 == 0 || detailSuratTugas.apv_es3 == 1 || detailSuratTugas.apv_es3 == 0 || detailSuratTugas.tte == 1) {
                            btnCancel.visibility = View.GONE
                            btnTolak.visibility = View.GONE
                            btnSetuju.visibility = View.GONE
                            btnTte.visibility = View.GONE
//                            btnLihatSp.visibility = View.GONE
                        } else {
                            if (idRule == 5 || idRule == 24) {
                                btnCancel.visibility = View.GONE
                                btnTolak.visibility = View.GONE
                                btnSetuju.visibility = View.GONE
                                btnTte.visibility = View.GONE
                            } else if (idRule == 1 || idRule == 2 || idRule == 12) {
                                if (detailSuratTugas.apv_es4 != 2 && detailSuratTugas.approve_id_user_eselon_4 == USERID_DETAIL) {
                                    btnCancel.visibility = View.VISIBLE
                                    btnTolak.visibility = View.GONE
                                    btnSetuju.visibility = View.GONE
                                } else if (detailSuratTugas.apv_es4 == 1 || detailSuratTugas.apv_es4 == 0) {
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
                    }
                    "ESELON III-A", "ESELON III-B" -> {
                        if (detailSuratTugas.apv_es4 == 0 || detailSuratTugas.apv_es2 == 1 || detailSuratTugas.apv_es2 == 0 || detailSuratTugas.apv_es1 == 1 || detailSuratTugas.apv_es1 == 0 || detailSuratTugas.tte == 1) {
                            btnCancel.visibility = View.GONE
                            btnTolak.visibility = View.GONE
                            btnSetuju.visibility = View.GONE
                            btnTte.visibility = View.GONE
//                            btnLihatSp.visibility = View.GONE
                        } else {
                            if (idRule == 5 || idRule == 24) {
                                btnCancel.visibility = View.GONE
                                btnTolak.visibility = View.GONE
                                btnSetuju.visibility = View.GONE
                                btnTte.visibility = View.GONE
                            } else {
                                if (detailSuratTugas.apv_es3 != 2 && detailSuratTugas.approve_id_user_eselon_3 == USERID_DETAIL) {
                                    btnCancel.visibility = View.VISIBLE
                                    btnTolak.visibility = View.GONE
                                    btnSetuju.visibility = View.GONE
                                } else if (detailSuratTugas.apv_es3 == 1 || detailSuratTugas.apv_es3 == 0) {
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
                    }
                    "ESELON II-A", "ESELON II-B" -> {
                        if (detailSuratTugas.apv_es3 == 0 || detailSuratTugas.apv_es4 == 0 || detailSuratTugas.apv_es1 == 1 || detailSuratTugas.apv_es1 == 0 || detailSuratTugas.tte == 1) {
                            btnCancel.visibility = View.GONE
                            btnTolak.visibility = View.GONE
                            btnSetuju.visibility = View.GONE
                            btnTte.visibility = View.GONE
//                            btnLihatSp.visibility = View.GONE
                        } else {
                            if (idRule == 5 || idRule == 24) {
                                btnCancel.visibility = View.GONE
                                btnTolak.visibility = View.GONE
                                btnSetuju.visibility = View.GONE
                                btnTte.visibility = View.GONE
                            } else {
                                if (detailSuratTugas.apv_es2 != 2 && detailSuratTugas.approve_id_user_eselon_2 == USERID_DETAIL) {
                                    btnCancel.visibility = View.VISIBLE
                                    btnTolak.visibility = View.GONE
                                    btnSetuju.visibility = View.GONE
                                    if (detailSuratTugas.apv_es2 == 1 && detailSuratTugas.tte == null) {
                                        btnTte.visibility = View.VISIBLE
                                    } else {
                                        btnTte.visibility = View.GONE
                                    }
                                } else if (detailSuratTugas.apv_es2 == 1 || detailSuratTugas.apv_es2 == 0) {
                                    btnCancel.visibility = View.GONE
                                    btnTolak.visibility = View.GONE
                                    btnSetuju.visibility = View.GONE
                                    btnTte.visibility = View.GONE
                                } else {
                                    btnCancel.visibility = View.GONE
                                    btnTolak.visibility = View.VISIBLE
                                    btnSetuju.visibility = View.VISIBLE
                                    btnTte.visibility = View.GONE
                                }
                            }

                        }
                    }
                    "ESELON I-A", "ESELON I-B" -> {
                        if (detailSuratTugas.apv_es2 == 0 || detailSuratTugas.apv_es4 == 0 || detailSuratTugas.apv_es3 == 0 || detailSuratTugas.tte == 1) {
                            btnCancel.visibility = View.GONE
                            btnTolak.visibility = View.GONE
                            btnSetuju.visibility = View.GONE
                            btnTte.visibility = View.GONE
//                            btnLihatSp.visibility = View.GONE
                        } else {
                            if (idRule == 5 || idRule == 24) {
                                btnCancel.visibility = View.GONE
                                btnTolak.visibility = View.GONE
                                btnSetuju.visibility = View.GONE
                                btnTte.visibility = View.GONE
                            } else {
                                if (detailSuratTugas.apv_es1 != 2 && detailSuratTugas.approve_id_user_eselon_1 == USERID_DETAIL) {
                                    btnCancel.visibility = View.VISIBLE
                                    btnTolak.visibility = View.GONE
                                    btnSetuju.visibility = View.GONE
                                    if (detailSuratTugas.apv_es2 == 1 && detailSuratTugas.tte == null) {
                                        btnTte.visibility = View.VISIBLE
                                    } else {
                                        btnTte.visibility = View.GONE
                                    }
                                } else {
                                    btnCancel.visibility = View.GONE
                                    btnTolak.visibility = View.VISIBLE
                                    btnSetuju.visibility = View.VISIBLE
                                    btnTte.visibility = View.GONE
                                }
                            }
                        }
                    }
                    else -> {
                        btnCancel.visibility = View.GONE
                        btnTolak.visibility = View.GONE
                        btnSetuju.visibility = View.GONE
                        btnTte.visibility = View.GONE
//                        btnLihatSp.visibility = View.GONE
                    }
                }

                when (detailSuratTugas.apv_es4) {
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

                when (detailSuratTugas.apv_es3) {
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

                when (detailSuratTugas.apv_es2) {
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

                when (detailSuratTugas.apv_es1) {
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
                val mAlertDialog = AlertDialog.Builder(this@DetailSuratTugasActivity)
                mAlertDialog.setTitle("Perhatian")
                mAlertDialog.setMessage("Apakah Anda yakin menyetujui surat tugas ini?")
                mAlertDialog.setIcon(R.drawable.ic_warning)

                mAlertDialog.setPositiveButton("Ya") { _, _ ->
                    Handler(mainLooper).postDelayed({
                        detailSuratTugasViewModel.setDetail(idST)
                        detailSuratTugasViewModel.getDetail().observe(this, {
                            showDetailTourism(it?.get(0))
                            binding.etNote.setText("")
                        })
                    }, 1000)
                    try {
                        detailSuratTugasViewModel.approvalST(
                            idPegawai,
                            detailSuratTugas.id_st,
                            idRule,
                            1,
                            binding.etNote.text.toString()
                        )
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

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
                val mAlertDialog = AlertDialog.Builder(this@DetailSuratTugasActivity)
                mAlertDialog.setTitle("Perhatian")
                mAlertDialog.setMessage("Apakah Anda yakin menolak surat tugas ini?")
                mAlertDialog.setIcon(R.drawable.ic_warning)
                mAlertDialog.setPositiveButton("Ya") { _, _ ->
                    Handler(mainLooper).postDelayed({
                        detailSuratTugasViewModel.setDetail(idST)
                        detailSuratTugasViewModel.getDetail().observe(this, {
                            showDetailTourism(it?.get(0))
                            binding.etNote.setText("")
                        })
                    }, 1000)
                    try {
                        detailSuratTugasViewModel.approvalST(
                            idPegawai,
                            detailSuratTugas.id_st,
                            idRule,
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
                val mAlertDialog = AlertDialog.Builder(this@DetailSuratTugasActivity)
                mAlertDialog.setTitle("Perhatian")
                mAlertDialog.setMessage("Apakah Anda yakin membatalkan tanggapan surat tugas ini?")
                mAlertDialog.setIcon(R.drawable.ic_warning)
                mAlertDialog.setPositiveButton("Ya") { _, _ ->
                    Handler(mainLooper).postDelayed({
                        detailSuratTugasViewModel.setDetail(idST)
                        detailSuratTugasViewModel.getDetail().observe(this, {
                            showDetailTourism(it?.get(0))
                            binding.etNote.setText("")
                        })
                    }, 1000)
                    try {
                        detailSuratTugasViewModel.approvalST(
                            idPegawai,
                            detailSuratTugas.id_st,
                            idRule,
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

    override fun onResume() {
        super.onResume()

        try {
            detailSuratTugasViewModel.setDetail(idST)
            detailSuratTugasViewModel.getDetail().observe(this, {
                showDetailTourism(it?.get(0))
                //jumlah petugas tidak ada pada json terkait
                jmlPtgs = 0/*it?.get(1)?.jumlahpetugas!!*/
                binding.etNote.setText("")
            })
        } catch (e: Exception) {
            Toast.makeText(this, "Error: $e", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_surat_tugas -> {
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(
                        Uri.parse("http://aplikasistore.org/api/surattugas/pdf?id_st=${idST}&pdf=true&token=b91dc65721c83b94cf5683b1afea84ba8225a7e98d85e2a6e34d8c9868995e41"),
                        "application/pdf"
                    )
                    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    startActivity(Intent.createChooser(intent, "Open with..."))
                } catch (e: Exception) {
                    Toast.makeText(this, "Error: $e,", Toast.LENGTH_LONG)
                        .show()
                }


            }
            R.id.btn_tte -> {
                try {
                    val intent = Intent(this, TteActivity::class.java)
                    intent.putExtra(ID_ST, idST)
                    intent.putExtra(NIK_TTE, nik)
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "Error: $e", Toast.LENGTH_SHORT).show()
                }
            }
            //untuk button lihat RKD
//            R.id.btn_lihat_rkd -> {
//                Toast.makeText(this, "Coming soon..", Toast.LENGTH_SHORT).show()
////                val intent = Intent(this, RKDActivity::class.java)
////                intent.putExtra(USERNAME_RKD, userName)
////                startActivity(intent)
//                try {
//
//                } catch (e: Exception) {
//                    Toast.makeText(this, "Error: $e", Toast.LENGTH_SHORT).show()
//                }
//            }
            R.id.btn_surat_pengantar -> {
                try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(
                        Uri.parse("http://aplikasistore.org/api/suratpengantar/pdf?id_st=${idST}&pdf=true&token=b91dc65721c83b94cf5683b1afea84ba8225a7e98d85e2a6e34d8c9868995e41"),
                        "application/pdf"
                    )
                    intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    startActivity(Intent.createChooser(intent, "Open with..."))
                } catch (e: Exception) {
                    Toast.makeText(this, "Error: $e,", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}
