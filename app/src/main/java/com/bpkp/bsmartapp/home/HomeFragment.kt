package com.bpkp.bsmartapp.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bpkp.bsmartapp.databinding.FragmentHomeBinding
import com.bpkp.bsmartapp.detail.DetailSuratTugasActivity
import com.bpkp.bsmartapp.detail.DetailSuratTugasActivity.Companion.ESELON_DETAIL
import com.bpkp.bsmartapp.detail.DetailSuratTugasActivity.Companion.USERNAME_DETAIL
import com.bpkp.bsmartapp.login.Constant
import com.bpkp.bsmartapp.login.PrefHelper
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), SuratTugasListener {
    lateinit var prefHelper: PrefHelper

    companion object {
        var USERNAME_HOME = "USERNAME_HOME"
        var NAME_HOME = "NAME_HOME"
        var ESELON_HOME = "ESELON_HOME"
    }

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var suratTugasAdapter: SuratTugasAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        suratTugasAdapter = SuratTugasAdapter()
        homeViewModel = HomeViewModel()
        suratTugasAdapter.notifyDataSetChanged()
        binding.tvName.text = NAME_HOME
        binding.tvGrade.text = ESELON_HOME

        if (activity != null) {

            suratTugasAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailSuratTugasActivity::class.java)
                intent.putExtra(DetailSuratTugasActivity.EXTRA_DATA, selectedData)
                intent.putExtra(USERNAME_DETAIL, USERNAME_HOME)
                intent.putExtra(ESELON_DETAIL, ESELON_HOME)
                startActivity(intent)
            }

            with(binding.rvSt) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = suratTugasAdapter
            }


            if (binding.etSearchSt.text.toString().isEmpty()) {
                homeViewModel.suratTugas(USERNAME_HOME)
                homeViewModel.getSuratTugas().observe(viewLifecycleOwner, {
                    if (it != null) {
                        suratTugasAdapter.setData(it)
                        binding.progressBar.visibility = View.GONE
                    }
                })
            }

            binding.cbFilter.setOnCheckedChangeListener { _, _ ->
                if (binding.cbFilter.isChecked) {
                    homeViewModel.suratTugasFilter(USERNAME_HOME)
                    homeViewModel.getSuratTugas().observe(viewLifecycleOwner, {
                        if (it != null) {
                            suratTugasAdapter.setData(it)
                            binding.progressBar.visibility = View.GONE
                        }
                    })
                } else {
                    homeViewModel.suratTugas(USERNAME_HOME)
                    homeViewModel.getSuratTugas().observe(viewLifecycleOwner, {
                        if (it != null) {
                            suratTugasAdapter.setData(it)
                            binding.progressBar.visibility = View.GONE
                        }
                    })
                }
            }

            prefHelper = PrefHelper(requireContext())
            if (binding.cbFilter.isChecked) {
                prefHelper.put(Constant.PREF_FILTER, true)
            }else{
                prefHelper.put(Constant.PREF_FILTER, false)
            }

            binding.apply {
                ivSearch.setOnClickListener {
                    searchUser()
                }
                etSearchSt.setOnKeyListener { _, keyCode, event ->
                    if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                        val imm: InputMethodManager =
                            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(etSearchSt.windowToken, 0)
                        searchUser()
                        return@setOnKeyListener true
                    }
                    return@setOnKeyListener false
                }
            }
        }

    }

    private fun searchUser() {
        binding.apply {
            val query = etSearchSt.text.toString()
            if (query.isEmpty()) return
            binding.progressBar.visibility = View.VISIBLE
            homeViewModel.setSearchSuratTugas(USERNAME_HOME, query)
            homeViewModel.tampilCari().observe(viewLifecycleOwner) {
                if (it.isNullOrEmpty()) {
                    suratTugasAdapter.setData(it)
                    binding.progressBar.visibility = View.GONE
                    binding.viewEmpty.root.visibility = View.VISIBLE
                    binding.rvSt.visibility = View.GONE

                } else {
                    binding.viewEmpty.root.visibility = View.GONE
                    binding.rvSt.visibility = View.VISIBLE
                    suratTugasAdapter.setData(it)
                    binding.progressBar.visibility = View.GONE
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()
        prefHelper = PrefHelper(requireContext())
//        if (prefHelper.getBoolean(Constant.PREF_FILTER)) {
//            homeViewModel.suratTugasFilter(USERNAME_HOME)
//            homeViewModel.getSuratTugas().observe(viewLifecycleOwner, {
//                if (it != null) {
//                    suratTugasAdapter.setData(it)
//                    binding.progressBar.visibility = View.GONE
//                }
//            })
//        }
        when(prefHelper.getBoolean(Constant.PREF_FILTER)){
            false -> {
                homeViewModel.suratTugasFilter(USERNAME_HOME)
                homeViewModel.getSuratTugas().observe(viewLifecycleOwner, {
                    if (it != null) {
                        suratTugasAdapter.setData(it)
                        binding.progressBar.visibility = View.GONE
                    }
                })
            }
            true ->{
                homeViewModel.suratTugas(USERNAME_HOME)
                homeViewModel.getSuratTugas().observe(viewLifecycleOwner, {
                    if (it != null) {
                        suratTugasAdapter.setData(it)
                        binding.progressBar.visibility = View.GONE
                    }
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}
