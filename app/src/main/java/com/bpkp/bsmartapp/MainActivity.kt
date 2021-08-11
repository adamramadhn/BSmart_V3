package com.bpkp.bsmartapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bpkp.bsmartapp.databinding.ActivityMainBinding
import com.bpkp.bsmartapp.detail.DetailSuratTugasActivity.Companion.USERID_DETAIL
import com.bpkp.bsmartapp.home.HomeFragment.Companion.ESELON_HOME
import com.bpkp.bsmartapp.home.HomeFragment.Companion.NAME_HOME
import com.bpkp.bsmartapp.home.HomeFragment.Companion.USERNAME_HOME
import com.bpkp.bsmartapp.login.PrefHelper
import com.bpkp.bsmartapp.profile.ProfileFragment.Companion.ESELON_USER
import com.bpkp.bsmartapp.profile.ProfileFragment.Companion.NAMA_USER
import com.bpkp.bsmartapp.profile.ProfileFragment.Companion.NIP_USER

class MainActivity : AppCompatActivity() {
    lateinit var prefHelper: PrefHelper
    private var backPressedTime: Long = 0
    companion object {
        val USERNAME_MAIN = "USERNAME_MAIN"
        val NAME_MAIN = "NAME_MAIN"
        val ESELON_MAIN = "ESELON_MAIN"
        val NIP_MAIN = "NIP_MAIN"
        val USERID_MAIN = "USERID_MAIN"
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //ALIASES
        prefHelper = PrefHelper(this)

        //fragment ST
        NAME_HOME = intent.getStringExtra(NAME_MAIN).toString()
        ESELON_HOME = intent.getStringExtra(ESELON_MAIN).toString()
        USERNAME_HOME = intent.getStringExtra(USERNAME_MAIN).toString()
        USERID_DETAIL = intent.getStringExtra(USERID_MAIN).toString()

        //fragment profile
        NIP_USER = intent.getStringExtra(NIP_MAIN).toString()
        NAMA_USER = intent.getStringExtra(NAME_MAIN).toString()
        ESELON_USER = intent.getStringExtra(ESELON_MAIN).toString()

        val navController = findNavController(R.id.fragmentContainerView)

        binding.bottomNavigationView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finish()
            super.onBackPressed()
            return
        } else {
           Toast.makeText(baseContext, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}

