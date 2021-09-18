package com.bpkp.bsmartapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bpkp.bsmartapp.databinding.ActivityMainBinding
import com.bpkp.bsmartapp.detail.DetailSuratTugasActivity.Companion.USERID_DETAIL
import com.bpkp.bsmartapp.home.HomeFragment.Companion.ESELON_HOME
import com.bpkp.bsmartapp.home.HomeFragment.Companion.ID_RULE_HOME
import com.bpkp.bsmartapp.home.HomeFragment.Companion.NAME_HOME
import com.bpkp.bsmartapp.home.HomeFragment.Companion.NIK_HOME
import com.bpkp.bsmartapp.home.HomeFragment.Companion.USERID_HOME
import com.bpkp.bsmartapp.login.PrefHelper
import com.bpkp.bsmartapp.profile.ProfileFragment.Companion.ESELON_USER
import com.bpkp.bsmartapp.profile.ProfileFragment.Companion.ID_RULE1P
import com.bpkp.bsmartapp.profile.ProfileFragment.Companion.ID_RULE2P
import com.bpkp.bsmartapp.profile.ProfileFragment.Companion.ID_RULE3P
import com.bpkp.bsmartapp.profile.ProfileFragment.Companion.ID_RULE4P
import com.bpkp.bsmartapp.profile.ProfileFragment.Companion.ID_RULE_PROFILE
import com.bpkp.bsmartapp.profile.ProfileFragment.Companion.NAMA_RULE1P
import com.bpkp.bsmartapp.profile.ProfileFragment.Companion.NAMA_RULE2P
import com.bpkp.bsmartapp.profile.ProfileFragment.Companion.NAMA_RULE3P
import com.bpkp.bsmartapp.profile.ProfileFragment.Companion.NAMA_RULE4P
import com.bpkp.bsmartapp.profile.ProfileFragment.Companion.NAMA_RULE_PROFILE
import com.bpkp.bsmartapp.profile.ProfileFragment.Companion.NAMA_USER
import com.bpkp.bsmartapp.profile.ProfileFragment.Companion.NIK_USER
import com.bpkp.bsmartapp.profile.ProfileFragment.Companion.NIP_USER

class MainActivity : AppCompatActivity() {
    lateinit var prefHelper: PrefHelper
    private var backPressedTime: Long = 0

    companion object {
        val USERNAME_MAIN = "USERNAME_MAIN"
        const val NAME_MAIN = "NAME_MAIN"
        const val ESELON_MAIN = "ESELON_MAIN"
        const val NIP_MAIN = "NIP_MAIN"
        const val USERID_MAIN = "USERID_MAIN"
        const val NIK = "NIK"
        const val NAMA_RULE = "NAMA_RULE"
        const val ID_RULE = "ID_RULE"
        const val NAMA_RULE1 = "NAMA_RULE1"
        const val ID_RULE1 = "ID_RULE1"
        const val NAMA_RULE2 = "NAMA_RULE2"
        const val ID_RULE2 = "ID_RULE2"
        const val NAMA_RULE3 = "NAMA_RULE3"
        const val ID_RULE3 = "ID_RULE3"
        const val NAMA_RULE4 = "NAMA_RULE4"
        const val ID_RULE4 = "ID_RULE4"
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
        USERID_HOME = intent.getStringExtra(USERID_MAIN).toString()
        USERID_DETAIL = intent.getStringExtra(USERID_MAIN).toString()

        ID_RULE_HOME = intent.getIntExtra(ID_RULE, 0)

        //fragment profile
        NIP_USER = intent.getStringExtra(NIP_MAIN).toString()
        NAMA_USER = intent.getStringExtra(NAME_MAIN).toString()
        ESELON_USER = intent.getStringExtra(ESELON_MAIN).toString()
        NAMA_RULE_PROFILE = intent.getStringExtra(NAMA_RULE).toString()
        ID_RULE_PROFILE = intent.getStringExtra(ID_RULE).toString()

        //Rule
        NAMA_RULE1P = intent.getStringExtra(NAMA_RULE1).toString()
        ID_RULE1P = intent.getIntExtra(ID_RULE1, 0)
        NAMA_RULE2P = intent.getStringExtra(NAMA_RULE2).toString()
        ID_RULE2P = intent.getIntExtra(ID_RULE2,0)
        NAMA_RULE3P = intent.getStringExtra(NAMA_RULE3).toString()
        ID_RULE3P = intent.getIntExtra(ID_RULE3,0)
        NAMA_RULE4P = intent.getStringExtra(NAMA_RULE4).toString()
        ID_RULE4P = intent.getIntExtra(ID_RULE4,0)

        NIK_USER = intent.getStringExtra(NIK).toString()
        NIK_HOME = intent.getStringExtra(NIK).toString()

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

