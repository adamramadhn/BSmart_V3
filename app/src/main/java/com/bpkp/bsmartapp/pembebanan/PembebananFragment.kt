package com.bpkp.bsmartapp.pembebanan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bpkp.bsmartapp.core.data.source.remote.network.ApiService
import com.bpkp.bsmartapp.core.data.source.remote.response.ListSuratTugasResponse
import com.bpkp.bsmartapp.core.data.source.remote.response.pembebanan.PembebananResponse
import com.bpkp.bsmartapp.databinding.FragmentPembebananBinding
import com.bpkp.bsmartapp.detail.DetailSuratTugasActivity
import com.bpkp.bsmartapp.home.HomeFragment
import com.bpkp.bsmartapp.home.SuratTugasAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PembebananFragment : Fragment(){
    private lateinit var binding: FragmentPembebananBinding
    private var page = 1
    private var items = 0
    private var totalPage = 0
    private var isLoading = false

    private lateinit var pembebananAdapter: PembebananAdapter
    companion object {
        var USERID_BEBAN = "USERID_HOME"
        var NAME_BEBAN = "NAME_HOME"
        var ESELON_BEBAN = "ESELON_HOME"
        var NIK_BEBAN = "NIK_HOME"
        var ID_RULE_BEBAN = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPembebananBinding.inflate(inflater, container, false)
        return binding.root
    }
    private lateinit var suratTugasAdapter: SuratTugasAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.GONE
        pembebananAdapter = PembebananAdapter()

        if(activity != null){
            pembebananAdapter.onItemClick = {
//                    selectedData ->
//                val intent = Intent(activity, DetailSuratTugasActivity::class.java)
//                intent.putExtra(DetailSuratTugasActivity.EXTRA_DATA, selectedData)
////                intent.putExtra(USERNAME_DETAIL, USERID_HOME)
//                intent.putExtra(DetailSuratTugasActivity.ESELON_DETAIL, HomeFragment.ESELON_HOME)
//                intent.putExtra(DetailSuratTugasActivity.NIK_DETAIL, HomeFragment.NIK_HOME)
//                startActivity(intent)
            }
            with(binding.rvPembebanan){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = pembebananAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        val visibleItemCount = (layoutManager as LinearLayoutManager).childCount

                        val pastVisibleItem =
                            (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                        if (!isLoading && page < totalPage) {
                            if (visibleItemCount + pastVisibleItem >= items) {
                                page++
                            }
                        }
                        super.onScrolled(recyclerView, dx, dy)
                    }
                })
            }
        }
    }
    private fun getListST() {
        isLoading = true
        binding.progressBar.visibility = View.VISIBLE
        ApiService().getBebanList(HomeFragment.USERID_HOME, HomeFragment.ID_RULE_HOME).enqueue(object :
            Callback<PembebananResponse> {
            override fun onResponse(
                call: Call<PembebananResponse>,
                response: Response<PembebananResponse>
            ) {
                try {
                    totalPage = response.body()?.pembebanan?.last_page!!
                    items = response.body()?.pembebanan?.to!!
                    val listResponse = response.body()?.pembebanan?.data

                    if (listResponse != null) {
                        pembebananAdapter.addList(listResponse)
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

            override fun onFailure(call: Call<PembebananResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: $t", Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onResume() {
        super.onResume()
        page = 1
        binding.cbFilter.isChecked = false
        pembebananAdapter.clear()
        getListST()
    }

}