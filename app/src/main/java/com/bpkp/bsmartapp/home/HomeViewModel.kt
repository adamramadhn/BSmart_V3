package com.bpkp.bsmartapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bpkp.bsmartapp.core.data.source.remote.network.ApiService
import com.bpkp.bsmartapp.core.data.source.remote.response.ListSuratTugasResponse
import com.bpkp.bsmartapp.core.data.source.remote.response.SuratTugasResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {
    var suratTugasListener: SuratTugasListener? = null
    val loginResponse = MutableLiveData<List<SuratTugasResponse>>()
    val cariResponse = MutableLiveData<List<SuratTugasResponse>>()

    fun suratTugas(user_email: String) {
        viewModelScope.launch {
            ApiService().getList(user_email).enqueue(object : Callback<ListSuratTugasResponse> {
                override fun onResponse(
                    call: Call<ListSuratTugasResponse>,
                    response: Response<ListSuratTugasResponse>
                ) {
                    if (response.isSuccessful) {
                        loginResponse.postValue(response.body()?.places)
                    }
                }

                override fun onFailure(call: Call<ListSuratTugasResponse>, t: Throwable) {
                }
            })
        }
    }

    fun getSuratTugas(): LiveData<List<SuratTugasResponse>> {
        return loginResponse
    }

    fun setSearchSuratTugas(user_email: String, cari: String): LiveData<List<SuratTugasResponse>> {
        ApiService().getSearch(user_email, cari)
            .enqueue(object : Callback<ListSuratTugasResponse> {
                override fun onFailure(call: Call<ListSuratTugasResponse>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<ListSuratTugasResponse>,
                    response: Response<ListSuratTugasResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.places.isNullOrEmpty()) {
                            suratTugasListener?.setMessage("Tidak ditemukan")
                            cariResponse.postValue(null)
                        } else {
                            cariResponse.postValue(response.body()?.places)
                        }
                    }
                }

            })
        return cariResponse
    }

    fun tampilCari(): LiveData<List<SuratTugasResponse>> {
        return cariResponse
    }

    fun suratTugasFilter(user_email: String) {
        ApiService().getFilter(user_email).enqueue(object : Callback<ListSuratTugasResponse> {
            override fun onResponse(
                call: Call<ListSuratTugasResponse>,
                response: Response<ListSuratTugasResponse>
            ) {
                if (response.isSuccessful) {
                    loginResponse.postValue(response.body()?.places)
                }
            }

            override fun onFailure(call: Call<ListSuratTugasResponse>, t: Throwable) {
            }
        })
    }
}