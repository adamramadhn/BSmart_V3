package com.bpkp.bsmartapp.rkd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bpkp.bsmartapp.databinding.ActivityRkdBinding
import com.bpkp.bsmartapp.home.HomeViewModel
import com.bpkp.bsmartapp.home.SuratTugasAdapter

class RKDActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRkdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRkdBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}