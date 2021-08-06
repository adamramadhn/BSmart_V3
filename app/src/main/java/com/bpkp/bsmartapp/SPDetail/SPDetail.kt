package com.bpkp.bsmartapp.SPDetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bpkp.bsmartapp.R
import com.bpkp.bsmartapp.databinding.DetailSuratPengantarBinding

class SPDetail : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: DetailSuratPengantarBinding
    private lateinit var SPViewModel: SPViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailSuratPengantarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener(this)
        binding.btnSuratPengantar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_surat_pengantar -> {
                Toast.makeText(this, "Surat pengantar...", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_back -> {
                finish()
            }
        }
    }
}