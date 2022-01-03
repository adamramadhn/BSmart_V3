package com.bpkp.bsmartapp.rkd

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bpkp.bsmartapp.R
import com.bpkp.bsmartapp.databinding.ActivityRkdBinding
import com.bpkp.bsmartapp.login.Constant
import com.bpkp.bsmartapp.login.PrefHelper
import com.bpkp.bsmartapp.pembebanan.ActivityPembebananDetail
//belum dipakai
class RKDActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRkdBinding
    private lateinit var rkdViewModel: RKDViewModel
    private lateinit var rkdAdapter: RKDAdapter
    lateinit var prefHelper: PrefHelper
    private var isLoading = false
    private var page = 1
    private var items = 0
    private var totalPage = 0

    companion object {
        const val USERNAME_RKD = "USERNAME_RKD"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRkdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener(this)
        rkdViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(RKDViewModel::class.java)
        rkdAdapter = RKDAdapter()

        val username = intent.getStringExtra(USERNAME_RKD)
        rkdAdapter.onItemClick = {
            val intent = Intent(this, ActivityPembebananDetail::class.java)
            intent.putExtra(ActivityPembebananDetail.EXTRA_DATA, it)
            intent.putExtra(USERNAME_RKD, username)
            startActivity(intent)
        }

        with(binding.rvSt) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = rkdAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val visibleItemCount = (layoutManager as LinearLayoutManager).childCount
                    val pastVisibleItem =
                        (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
//                    val total = rkdAdapter.itemCount
                    if (!isLoading && page < totalPage) {
                        if (visibleItemCount + pastVisibleItem >= items) {
                            page++
                            if (!prefHelper.getBoolean(Constant.PREF_FILTER)) {
                                getListRkd()
                            } else {
                                getFilterRkd()
                            }
                        }
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            })
        }
        prefHelper = PrefHelper(this)
        binding.cbFilter.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                prefHelper.put(Constant.PREF_FILTER, true)
                getFilterRkd()
            } else {
                prefHelper.put(Constant.PREF_FILTER, false)
                getListRkd()
            }
        }

//        if (binding.etSearchSt.text.toString().isEmpty()) {
//            if (username != null) {
//                rkdViewModel.suratTugas(username)
//                rkdViewModel.getSuratTugas().observe(this, {
//                    if (it != null) {
//                        rkdAdapter.setData(it)
//                        binding.progressBar.visibility = View.GONE
//                    }
//                })
//            }
//        }

        binding.apply {
            ivSearch.setOnClickListener {
                searchUser()
            }
            etSearchSt.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    val imm: InputMethodManager =
                        this@RKDActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(etSearchSt.windowToken, 0)
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> {
                finish()
            }
        }
    }

    private fun searchUser() {
        binding.apply {
            val query = etSearchSt.text.toString()
            if (query.isEmpty()) return
            binding.progressBar.visibility = View.VISIBLE
            getSearch(query)
        }
    }

    private fun getSearch(query: String) {
        TODO("Not yet implemented")
    }

    private fun getRkd() {
//        ApiService().getList()
    }

    override fun onResume() {
        super.onResume()
        prefHelper = PrefHelper(this)
        if (prefHelper.getBoolean(Constant.PREF_FILTER)) {
            getFilterRkd()
        } else {
            getListRkd()
        }
    }

    private fun getListRkd() {
        TODO("Not yet implemented")
    }

    private fun getFilterRkd() {
        TODO("Not yet implemented")
    }
}