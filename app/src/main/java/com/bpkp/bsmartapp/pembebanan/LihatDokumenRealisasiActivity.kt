package com.bpkp.bsmartapp.pembebanan


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bpkp.bsmartapp.R
import com.bpkp.bsmartapp.databinding.ActivityBtnLihatDokumenRealisasiBinding
import com.bpkp.bsmartapp.databinding.ActivityDetailPembebananBinding

class LihatDokumenRealisasiActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityBtnLihatDokumenRealisasiBinding

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val USERNAME_RKD = "USERNAME_RKD"
    }

    private var userName: String? = ""
    private var userEselon: String? = ""
    private var idST: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBtnLihatDokumenRealisasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> {
                finish()
            }
        }
    }


}