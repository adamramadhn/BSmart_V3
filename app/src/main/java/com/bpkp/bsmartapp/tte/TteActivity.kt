package com.bpkp.bsmartapp.tte

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bpkp.bsmartapp.databinding.ActivityTteBinding

class TteActivity: AppCompatActivity(), View.OnClickListener {
    private lateinit var tteBinding: ActivityTteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tteBinding = ActivityTteBinding.inflate(layoutInflater)
        setContentView(tteBinding.root)


    }
    override fun onClick(v: View?) {

    }

}