package com.bpkp.bsmartapp.rkd

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.bpkp.bsmartapp.databinding.ActivityRkdBinding
import com.bpkp.bsmartapp.detail.DetailRKDActivity
import com.bpkp.bsmartapp.detail.DetailSuratTugasActivity
import com.bpkp.bsmartapp.home.HomeFragment
import com.bpkp.bsmartapp.home.HomeViewModel
import com.bpkp.bsmartapp.home.SuratTugasAdapter

class RKDActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRkdBinding
    private lateinit var RKDViewModel: RKDViewModel
    private lateinit var RKDAdapter: RKDAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRkdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        RKDAdapter = RKDAdapter()
        RKDViewModel = RKDViewModel()
        RKDAdapter.notifyDataSetChanged()

        with(binding.rvSt) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = RKDAdapter
        }

        if (binding.etSearchSt.text.toString().isEmpty()) {
            RKDViewModel.suratTugas(HomeFragment.USERNAME_HOME)
            RKDViewModel.getSuratTugas().observe(this, {
                if (it != null) {
                    RKDAdapter.setData(it)
                    binding.progressBar.visibility = View.GONE
                }
            })
        }
    }



}