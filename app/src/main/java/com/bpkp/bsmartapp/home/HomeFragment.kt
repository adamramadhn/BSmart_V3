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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bpkp.bsmartapp.core.data.source.remote.network.ApiService
import com.bpkp.bsmartapp.core.data.source.remote.response.ListSuratTugasResponse
import com.bpkp.bsmartapp.databinding.FragmentHomeBinding
import com.bpkp.bsmartapp.detail.DetailSuratTugasActivity
import com.bpkp.bsmartapp.detail.DetailSuratTugasActivity.Companion.ESELON_DETAIL
import com.bpkp.bsmartapp.detail.DetailSuratTugasActivity.Companion.USERNAME_DETAIL
import com.bpkp.bsmartapp.login.Constant
import com.bpkp.bsmartapp.login.PrefHelper
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class HomeFragment : Fragment(), SuratTugasListener, SwipeRefreshLayout.OnRefreshListener {
    lateinit var prefHelper: PrefHelper
    private var isLoading = false
    private var page = 1
    private var items = 0
    private var totalPage = 0

    companion object {
        var USERNAME_HOME = "USERNAME_HOME"
        var NAME_HOME = "NAME_HOME"
        var ESELON_HOME = "ESELON_HOME"
    }

//    private lateinit var homeViewModel: HomeViewModel
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
//        homeViewModel = ViewModelProvider(
//            this,
//            ViewModelProvider.NewInstanceFactory()
//        ).get(HomeViewModel::class.java)

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
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        val visibleItemCount = (layoutManager as LinearLayoutManager).childCount

                        val pastVisibleItem =
                            (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                        if (!isLoading && page < totalPage) {
                            if (visibleItemCount + pastVisibleItem >= items) {
                                page++
                                if (!prefHelper.getBoolean(Constant.PREF_FILTER)) {
                                    getListST()
                                } else {
                                    getFilter()
                                }
                            }
                        }
                        super.onScrolled(recyclerView, dx, dy)
                    }
                })
            }
            binding.swipeRefresh.setOnRefreshListener(this)

            prefHelper = PrefHelper(requireContext())
            binding.cbFilter.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    prefHelper.put(Constant.PREF_FILTER, true)
                    getFilter()
                } else {
                    prefHelper.put(Constant.PREF_FILTER, false)
                    getListST()
                }
            }
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

    private fun searchUser() {
        binding.apply {
            val query = etSearchSt.text.toString()
            if (query.isEmpty()) return
            binding.progressBar.visibility = View.VISIBLE
            getSearch(query)
        }
    }

    override fun onResume() {
        super.onResume()
        prefHelper = PrefHelper(requireContext())
        if (prefHelper.getBoolean(Constant.PREF_FILTER)) {
            getFilter()
        } else {
            getListST()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun getListST() {
        isLoading = true
        binding.progressBar.visibility = View.VISIBLE
        suratTugasAdapter.clear()
        ApiService().getList(USERNAME_HOME, page).enqueue(object :
            Callback<ListSuratTugasResponse> {
            override fun onResponse(
                call: Call<ListSuratTugasResponse>,
                response: Response<ListSuratTugasResponse>
            ) {
                try {
                    totalPage = response.body()?.places?.last_page!!
                    items = response.body()?.places?.to!!
                    val listResponse = response.body()?.places?.data

                    if (listResponse != null) {
                        suratTugasAdapter.addList(listResponse)
                    }
                    binding.progressBar.visibility = View.VISIBLE
                    isLoading = false
                    if (page == totalPage) {
                        binding.progressBar.visibility = View.GONE
                    } else {
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error: $e", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<ListSuratTugasResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: $t", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getFilter() {
        isLoading = true
        binding.progressBar.visibility = View.VISIBLE
        suratTugasAdapter.clear()
        ApiService().getFilter(USERNAME_HOME, page)
            .enqueue(object : Callback<ListSuratTugasResponse> {
                override fun onResponse(
                    call: Call<ListSuratTugasResponse>,
                    response: Response<ListSuratTugasResponse>
                ) {
                    try {
                        totalPage = response.body()?.places?.last_page!!
                        items = response.body()?.places?.to!!
                        val filterResponse = response.body()?.places?.data
                        if (filterResponse != null) {
                            suratTugasAdapter.addList(filterResponse)
                        }
                        binding.progressBar.visibility = View.VISIBLE
                        isLoading = false
                        if (page == totalPage) {
                            binding.progressBar.visibility = View.GONE
                        } else {
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Error: $e", Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<ListSuratTugasResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error: $t", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getSearch(query: String) {
        ApiService().getSearch(USERNAME_HOME, query)
            .enqueue(object : Callback<ListSuratTugasResponse> {
                override fun onFailure(call: Call<ListSuratTugasResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "Error: $t,", Toast.LENGTH_LONG)
                        .show()
                    binding.progressBar.visibility = View.GONE
                }

                override fun onResponse(
                    call: Call<ListSuratTugasResponse>,
                    response: Response<ListSuratTugasResponse>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val listResponse = response.body()?.places?.data
                            if (listResponse != null) {
                                suratTugasAdapter.clear()
                                suratTugasAdapter.addList(listResponse)
                                binding.progressBar.visibility = View.GONE
                                binding.viewEmpty.root.visibility = View.GONE
                                binding.rvSt.visibility = View.VISIBLE
                            } else {
                                suratTugasAdapter.clear()
                                binding.progressBar.visibility = View.GONE
                                binding.viewEmpty.root.visibility = View.VISIBLE
                                binding.rvSt.visibility = View.GONE
                            }
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Error: $e,", Toast.LENGTH_LONG)
                            .show()
                        binding.progressBar.visibility = View.GONE
                    }
                }

            })
    }

    override fun onRefresh() {
        try {
            binding.progressBar.visibility = View.GONE
            suratTugasAdapter.clear()
            page = 1
            getListST()
            swipeRefresh.isRefreshing = false
        } catch (e: Exception) {
            swipeRefresh.isRefreshing = false
            Toast.makeText(requireContext(), "Error: $e,", Toast.LENGTH_LONG)
                .show()
            binding.progressBar.visibility = View.GONE
        }
    }


}
