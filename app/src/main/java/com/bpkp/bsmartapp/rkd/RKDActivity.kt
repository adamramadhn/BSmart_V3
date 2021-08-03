package com.bpkp.bsmartapp.rkd

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bpkp.bsmartapp.databinding.ActivityRkdBinding

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
        val username = intent.getStringExtra(USERNAME_RKD)
        rkdAdapter.onItemClick = {
            val intent = Intent(this,RKDDetail::class.java)
            intent.putExtra(RKDDetail.EXTRA_DATA, it)
            intent.putExtra(USERNAME_RKD,username)
            startActivity(intent)
        }

        with(binding.rvSt) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = rkdAdapter
        }

        if (binding.etSearchSt.text.toString().isEmpty()) {
            if (username != null) {
                rkdViewModel.suratTugas(username)
                rkdViewModel.getSuratTugas().observe(this, {
                    if (it != null) {
                        rkdAdapter.setData(it)
                        binding.progressBar.visibility = View.GONE
                    }
                })
            }

        }
    }
}