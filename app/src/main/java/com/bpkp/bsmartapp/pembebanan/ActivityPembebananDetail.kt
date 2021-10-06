package com.bpkp.bsmartapp.pembebanan

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bpkp.bsmartapp.R
import com.bpkp.bsmartapp.core.data.source.remote.response.pembebanan.PembebananDetail
import com.bpkp.bsmartapp.databinding.ActivityDetailPembebananBinding

class ActivityPembebananDetail : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailPembebananBinding
    private lateinit var pembebananDetailViewModel: PembebananDetailViewModel

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val ID_BEBAN = "ID_BEBAN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPembebananBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener(this)

        pembebananDetailViewModel =
            ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                PembebananDetailViewModel::class.java
            )

        val idBeban = intent.getIntExtra(ID_BEBAN, 0)
        binding.btnLihatRkd.setOnClickListener(this)
        try {
            pembebananDetailViewModel.setDetailBeban(idBeban)
            pembebananDetailViewModel.getDetailBeban().observe(this, {
                showDetailTourism(it?.get(0))
            })
        } catch (e: Exception) {
            Log.d("ZZZ", e.toString())
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> {
                finish()
            }
            R.id.btn_lihat_rkd -> {
                Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDetailTourism(detaiPembebanan: PembebananDetail?) {
        detaiPembebanan?.let {
            with(binding) {
                tvNoPembebanan.text = detaiPembebanan.no_beban
                val v = detaiPembebanan.tgl_buat
                val timeV = v?.dropLast(8)
                tvDate.text = timeV
                tvDescription.text = detaiPembebanan.untuk
                tvMakDipa.text = detaiPembebanan.id_mak
                if(detaiPembebanan.nilai.isNullOrEmpty()){
                    tvNilai.text = "Rp.0,-"
                }else{
                    tvNilai.text = "Rp.${detaiPembebanan.nilai},-"
                }

                tvStatus.text = "${detaiPembebanan.status_detail} - $timeV"
                tvJumlahPetugas.text = "-"
            }
        }
    }
}