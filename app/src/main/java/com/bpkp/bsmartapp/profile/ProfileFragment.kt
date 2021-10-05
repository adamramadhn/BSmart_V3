package com.bpkp.bsmartapp.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bpkp.bsmartapp.R
import com.bpkp.bsmartapp.databinding.FragmentProfileBinding
import com.bpkp.bsmartapp.home.HomeFragment.Companion.ID_RULE_HOME
import com.bpkp.bsmartapp.login.Constant
import com.bpkp.bsmartapp.login.LoginActivity
import com.bpkp.bsmartapp.login.PrefHelper
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(), View.OnClickListener/*, AdapterView.OnItemSelectedListener*/ {
    lateinit var prefHelper: PrefHelper

    companion object {
        var NAMA_USER = "NAMA_USER"
        var ESELON_USER = "ESELON_USER"
        var NIP_USER = "NIP_USER"
        var NIK_USER = "NIK_USER"
        var NAMA_RULE_PROFILE = "NAMA_RULE"
        var ID_RULE_PROFILE = "ID_RULE"

        var NAMA_RULE1P = "NAMA_RULE1P"
        var ID_RULE1P = 0
        var NAMA_RULE2P = "NAMA_RULE2P"
        var ID_RULE2P = 0
        var NAMA_RULE3P = "NAMA_RULE3P"
        var ID_RULE3P = 0
        var NAMA_RULE4P = "NAMA_RULE4P"
        var ID_RULE4P = 0
        var NAMA_RULE5P = "NAMA_RULE5P"
        var ID_RULE5P = 0

    }

    private lateinit var profileBinding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        return profileBinding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_name.text = NAMA_USER
        tv_eselon.text = ESELON_USER
        tv_nip.text = NIP_USER
        prefHelper = PrefHelper(requireContext())

        when {
            prefHelper.getBoolean(Constant.PREF_ROLE_RADIO1) -> {
                arr1.isChecked = true
                arr2.isChecked = false
                arr3.isChecked = false
                arr4.isChecked = false
                arr5.isChecked = false
            }
            prefHelper.getBoolean(Constant.PREF_ROLE_RADIO2) -> {
                arr1.isChecked = false
                arr2.isChecked = true
                arr3.isChecked = false
                arr4.isChecked = false
                arr5.isChecked = false
            }
            prefHelper.getBoolean(Constant.PREF_ROLE_RADIO3) -> {
                arr1.isChecked = false
                arr2.isChecked = false
                arr3.isChecked = true
                arr4.isChecked = false
                arr5.isChecked = false
            }
            prefHelper.getBoolean(Constant.PREF_ROLE_RADIO4) -> {
                arr1.isChecked = false
                arr2.isChecked = false
                arr3.isChecked = false
                arr4.isChecked = true
                arr5.isChecked = false
            }
            prefHelper.getBoolean(Constant.PREF_ROLE_RADIO5) -> {
                arr1.isChecked = false
                arr2.isChecked = false
                arr3.isChecked = false
                arr4.isChecked = false
                arr5.isChecked = true
            }
        }



        Log.d(
            "ZZZ",
            "$NAMA_RULE1P = $ID_RULE1P\n$NAMA_RULE2P = $ID_RULE2P\n$NAMA_RULE3P = $ID_RULE3P\n$NAMA_RULE4P = $ID_RULE4P\n" +
                    "$NAMA_RULE5P = $ID_RULE5P"
        )
        with(profileBinding) {
            arr1.text = NAMA_RULE1P
            arr2.text = NAMA_RULE2P
            arr3.text = NAMA_RULE3P
            arr4.text = NAMA_RULE4P
            arr5.text = NAMA_RULE5P
            if (ID_RULE2P == 0) {
                arr2.visibility = View.GONE
            }
            if (ID_RULE3P == 0) {
                arr3.visibility = View.GONE
            }
            if (ID_RULE4P == 0) {
                arr4.visibility = View.GONE
            }
            if (ID_RULE5P == 0) {
                arr5.visibility = View.GONE
            }
        }


        profileBinding.arr1.setOnClickListener(this)
        profileBinding.arr2.setOnClickListener(this)
        profileBinding.arr3.setOnClickListener(this)
        profileBinding.arr4.setOnClickListener(this)
        profileBinding.arr5.setOnClickListener(this)

        profileBinding.btnLogOut.setOnClickListener {
            val mAlertDialog = AlertDialog.Builder(requireActivity())
            mAlertDialog.setTitle("Peringatan")
            mAlertDialog.setMessage("Apakah Anda ingin keluar?")
            mAlertDialog.setIcon(R.drawable.ic_warning)
            mAlertDialog.setPositiveButton("Ya") { _, _ ->
                Toast.makeText(requireContext(), "Logout Successfully", Toast.LENGTH_SHORT).show()
                prefHelper.clear()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            mAlertDialog.setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            mAlertDialog.show()
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.arr1 -> {
                ID_RULE_HOME = ID_RULE1P
                prefHelper.put(Constant.PREF_ROLE_RADIO1, true)
                prefHelper.put(Constant.PREF_ROLE_RADIO2, false)
                prefHelper.put(Constant.PREF_ROLE_RADIO3, false)
                prefHelper.put(Constant.PREF_ROLE_RADIO4, false)
                prefHelper.put(Constant.PREF_ROLE_RADIO5, false)
            }
            R.id.arr2 -> {
                ID_RULE_HOME = ID_RULE2P
                prefHelper.put(Constant.PREF_ROLE_RADIO1, false)
                prefHelper.put(Constant.PREF_ROLE_RADIO2, true)
                prefHelper.put(Constant.PREF_ROLE_RADIO3, false)
                prefHelper.put(Constant.PREF_ROLE_RADIO4, false)
                prefHelper.put(Constant.PREF_ROLE_RADIO5, false)
            }
            R.id.arr3 -> {
                ID_RULE_HOME = ID_RULE3P
                prefHelper.put(Constant.PREF_ROLE_RADIO1, false)
                prefHelper.put(Constant.PREF_ROLE_RADIO2, false)
                prefHelper.put(Constant.PREF_ROLE_RADIO3, true)
                prefHelper.put(Constant.PREF_ROLE_RADIO4, false)
                prefHelper.put(Constant.PREF_ROLE_RADIO5, false)
            }
            R.id.arr4 -> {
                ID_RULE_HOME = ID_RULE4P
                prefHelper.put(Constant.PREF_ROLE_RADIO1, false)
                prefHelper.put(Constant.PREF_ROLE_RADIO2, false)
                prefHelper.put(Constant.PREF_ROLE_RADIO3, false)
                prefHelper.put(Constant.PREF_ROLE_RADIO4, true)
                prefHelper.put(Constant.PREF_ROLE_RADIO5, false)
            }
            R.id.arr5 -> {
                ID_RULE_HOME = ID_RULE5P
                prefHelper.put(Constant.PREF_ROLE_RADIO1, false)
                prefHelper.put(Constant.PREF_ROLE_RADIO2, false)
                prefHelper.put(Constant.PREF_ROLE_RADIO3, false)
                prefHelper.put(Constant.PREF_ROLE_RADIO4, false)
                prefHelper.put(Constant.PREF_ROLE_RADIO5, true)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        when(ID_RULE_HOME){
            ID_RULE1P -> {
                arr1.isChecked = true
            }
            ID_RULE2P -> {
                arr2.isChecked = true
            }
            ID_RULE3P -> {
                arr3.isChecked = true
            }
            ID_RULE4P -> {
                arr4.isChecked = true
            }
            ID_RULE5P -> {
                arr5.isChecked = true
            }
        }
    }
}

