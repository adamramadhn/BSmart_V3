package com.bpkp.bsmartapp.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bpkp.bsmartapp.R
import com.bpkp.bsmartapp.databinding.FragmentProfileBinding
import com.bpkp.bsmartapp.home.HomeFragment.Companion.ID_RULE_HOME
import com.bpkp.bsmartapp.login.LoginActivity
import com.bpkp.bsmartapp.login.PrefHelper
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment(), View.OnClickListener {
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
    }

//    private var nik = ""
//    private var passphrase = ""

    private lateinit var profileBinding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileBinding = FragmentProfileBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return profileBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_name.text = NAMA_USER
        tv_eselon.text = ESELON_USER
        tv_nip.text = NIP_USER
        prefHelper = PrefHelper(requireContext())
        Log.d(
            "ZZZ",
            "$NAMA_RULE1P = $ID_RULE1P\n$NAMA_RULE2P = $ID_RULE2P\n$NAMA_RULE3P = $ID_RULE3P\n$NAMA_RULE4P = $ID_RULE4P"
        )

        //
//
//        profileBinding.radioButton.setOnClickListener(this)
//        profileBinding.radioButton2.setOnClickListener(this)
//        //Admin = 1
//        profileBinding.roleAdminGrey.setOnClickListener {
//            with(profileBinding) {
//                ID_RULE_HOME = 1
//                roleAdminBlue.visibility = View.VISIBLE
//                rolePkuBlue.visibility = View.INVISIBLE
//                roleManagerBlue.visibility = View.INVISIBLE
//                rolePegawaiBlue.visibility = View.INVISIBLE
//                roleAdminUnitBlue.visibility = View.INVISIBLE
//
//                roleAdminUnitGrey.visibility = View.VISIBLE
//                roleAdminGrey.visibility = View.INVISIBLE
//                rolePkuGrey.visibility = View.VISIBLE
//                roleManagerGrey.visibility = View.VISIBLE
//                rolePegawaiGrey.visibility = View.VISIBLE
//            }
//        }
//        if (profileBinding.rolePegawaiBlue.visibility == View.VISIBLE) {
//            ID_RULE_HOME = 24
//        }
//
//        //Pegawai = 24
//        profileBinding.rolePegawaiGrey.setOnClickListener {
//            with(profileBinding) {
//                ID_RULE_HOME = 24
//                roleAdminBlue.visibility = View.INVISIBLE
//                rolePkuBlue.visibility = View.INVISIBLE
//                roleManagerBlue.visibility = View.INVISIBLE
//                rolePegawaiBlue.visibility = View.VISIBLE
//                roleAdminUnitBlue.visibility = View.INVISIBLE
//
//                roleAdminUnitGrey.visibility = View.VISIBLE
//                roleAdminGrey.visibility = View.VISIBLE
//                rolePkuGrey.visibility = View.VISIBLE
//                roleManagerGrey.visibility = View.VISIBLE
//                rolePegawaiGrey.visibility = View.INVISIBLE
//            }
//        }
//
//        //Manager = 12
//        profileBinding.roleManagerGrey.setOnClickListener {
//            with(profileBinding) {
//                ID_RULE_HOME = 12
//                roleAdminBlue.visibility = View.INVISIBLE
//                rolePkuBlue.visibility = View.INVISIBLE
//                roleManagerBlue.visibility = View.VISIBLE
//                rolePegawaiBlue.visibility = View.INVISIBLE
//                roleAdminUnitBlue.visibility = View.INVISIBLE
//
//                roleAdminUnitGrey.visibility = View.VISIBLE
//                roleAdminGrey.visibility = View.VISIBLE
//                rolePkuGrey.visibility = View.VISIBLE
//                roleManagerGrey.visibility = View.INVISIBLE
//                rolePegawaiGrey.visibility = View.VISIBLE
//            }
//        }
//
//
//        //ADMIN UNIT = 2
//        profileBinding.roleAdminUnitGrey.setOnClickListener {
//            with(profileBinding) {
//                ID_RULE_HOME = 2
//                roleAdminBlue.visibility = View.INVISIBLE
//                rolePkuBlue.visibility = View.INVISIBLE
//                roleManagerBlue.visibility = View.INVISIBLE
//                rolePegawaiBlue.visibility = View.INVISIBLE
//                roleAdminUnitBlue.visibility = View.VISIBLE
//
//                roleAdminUnitGrey.visibility = View.INVISIBLE
//                roleAdminGrey.visibility = View.VISIBLE
//                rolePkuGrey.visibility = View.VISIBLE
//                roleManagerGrey.visibility = View.VISIBLE
//                rolePegawaiGrey.visibility = View.VISIBLE
//
//            }
//        }
//
//
//        //PKU = 5
//        profileBinding.rolePkuGrey.setOnClickListener {
//            with(profileBinding) {
//                ID_RULE_HOME = 5
//                roleAdminBlue.visibility = View.INVISIBLE
//                rolePkuBlue.visibility = View.VISIBLE
//                roleManagerBlue.visibility = View.INVISIBLE
//                rolePegawaiBlue.visibility = View.INVISIBLE
//                roleAdminUnitBlue.visibility = View.INVISIBLE
//
//                roleAdminUnitGrey.visibility = View.VISIBLE
//                roleAdminGrey.visibility = View.VISIBLE
//                rolePkuGrey.visibility = View.INVISIBLE
//                roleManagerGrey.visibility = View.VISIBLE
//                rolePegawaiGrey.visibility = View.VISIBLE
//            }
//        }


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
//            R.id.radioButton -> {
//                ID_RULE_HOME = 24
//            }
//            R.id.radioButton2 -> {
//                ID_RULE_HOME = 12
//            }
        }
    }

}