package com.bpkp.bsmartapp.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bpkp.bsmartapp.MainActivity
import com.bpkp.bsmartapp.R
import com.bpkp.bsmartapp.core.data.source.remote.network.ApiService
import com.bpkp.bsmartapp.core.data.source.remote.response.DetailST
import com.bpkp.bsmartapp.databinding.FragmentProfileBinding
import com.bpkp.bsmartapp.home.HomeFragment.Companion.USERNAME_HOME
import com.bpkp.bsmartapp.login.Constant
import com.bpkp.bsmartapp.login.LoginActivity
import com.bpkp.bsmartapp.login.PrefHelper
import kotlinx.android.synthetic.main.fragment_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment(), View.OnClickListener {
    lateinit var prefHelper: PrefHelper

    companion object {
        var NAMA_USER = "NAMA_USER"
        var ESELON_USER = "ESELON_USER"
        var NIP_USER = "NIP_USER"
    }

    private var nik = ""
    private var passphrase = ""

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

        profileBinding.btnSubmit.setOnClickListener(this)

        profileBinding.btnLogOut.setOnClickListener() {
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
            R.id.btn_submit -> {
                nik = profileBinding.inputNIK.text?.trim().toString()
                passphrase = profileBinding.inputPASS.text?.trim().toString()
                Log.d("ZZZ", "Username: $USERNAME_HOME\n$nik\nPass: $passphrase")
                try {
                    ApiService().loginTte(USERNAME_HOME, nik, passphrase).enqueue(object :
                        Callback<DetailST> {
                        override fun onResponse(
                            call: Call<DetailST>,
                            response: Response<DetailST>
                        ) {
                            Toast.makeText(requireContext(), "${response.body()?.message}", Toast.LENGTH_SHORT).show()
                            profileBinding.inputNik.visibility = View.GONE
                            profileBinding.inputPassphrase.visibility = View.GONE
                            profileBinding.tvNik.visibility = View.GONE
                            profileBinding.tvPassphrase.visibility = View.GONE
                            profileBinding.textViewLoginTte.visibility = View.GONE
                            profileBinding.btnSubmit.visibility = View.GONE
                        }

                        override fun onFailure(call: Call<DetailST>, t: Throwable) {
                            Toast.makeText(requireContext(), "Error: $t", Toast.LENGTH_SHORT).show()
                        }

                    })
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error: $e", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}