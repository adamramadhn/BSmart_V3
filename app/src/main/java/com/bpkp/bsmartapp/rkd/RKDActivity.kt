package com.bpkp.bsmartapp.rkd

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bpkp.bsmartapp.databinding.ActivityRkdBinding
import com.bpkp.bsmartapp.home.HomeFragment

class RKDActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRkdBinding
    private lateinit var rkdViewModel: RKDViewModel
    private lateinit var rkdAdapter: RKDAdapter

    companion object {
        const val USERNAME_RKD = "USERNAME_RKD"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRkdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rkdViewModel = RKDViewModel()
        rkdAdapter = RKDAdapter()

        with(binding.rvSt) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = rkdAdapter
        }
//        val username = intent.getStringExtra(USERNAME_RKD)
        if (binding.etSearchSt.text.toString().isEmpty()) {
            rkdViewModel.suratTugas(HomeFragment.USERNAME_HOME)
            rkdViewModel.getSuratTugas().observe(this, {
                if (it != null) {
                    rkdAdapter.setData(it)
                    binding.progressBar.visibility = View.GONE
                }
            })
        }
    }
}