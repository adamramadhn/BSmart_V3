package com.bpkp.bsmartapp.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bpkp.bsmartapp.core.data.source.remote.network.ApiService
import com.bpkp.bsmartapp.core.data.source.remote.response.DetailST
import com.bpkp.bsmartapp.core.data.source.remote.response.SuratTugasResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailSuratTugasViewModel : ViewModel() {

    val loginResponse = MutableLiveData<SuratTugasResponse?>()
    val loginResponse2 = MutableLiveData<List<SuratTugasResponse>?>()
    val tteResponse = MutableLiveData<List<SuratTugasResponse>?>()
    val jumlahPetugas = MutableLiveData<Int?>()
    fun approvalST(
        user_email: String?,
        idST: Int,
        approval: Int,
        catatan: String?
    ) {
        if (user_email != null && catatan != null) {
            ApiService().approvalST(user_email, idST, approval, catatan)
                .enqueue(object : Callback<SuratTugasResponse> {
                    override fun onResponse(
                        call: Call<SuratTugasResponse>,
                        response: Response<SuratTugasResponse>
                    ) {
                        if (response.isSuccessful) {
                            loginResponse.postValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<SuratTugasResponse>, t: Throwable) {
                    }
                })
        }
    }

    fun getJumlahPetugas(): LiveData<Int?>{
        return jumlahPetugas
    }

    fun getDetail(): LiveData<List<SuratTugasResponse>?> {
        return loginResponse2
    }

    fun setDetail(user_email: String, idST: Int) {
        ApiService().getDetailST(user_email, idST)
            .enqueue(object : Callback<DetailST> {
                override fun onResponse(
                    call: Call<DetailST>,
                    response: Response<DetailST>
                ) {
                    if (response.isSuccessful) {
                        loginResponse2.postValue(response.body()?.places2)
                        jumlahPetugas.postValue(response.body()?.places2?.indexOf(1))
                    }
                }

                override fun onFailure(call: Call<DetailST>, t: Throwable) {

                }

            })
    }

    fun getTte(id_st: Int, Nik: String, PassPhrase: String) {
        ApiService().getTte(id_st, Nik, PassPhrase).enqueue(object : Callback<DetailST> {
            override fun onResponse(call: Call<DetailST>, response: Response<DetailST>) {
                tteResponse.postValue(response.body()?.places2)
            }

            override fun onFailure(call: Call<DetailST>, t: Throwable) {
                Log.d("ZZZ", "Error: $t")
            }

        })
    }

    fun tte(): LiveData<List<SuratTugasResponse>?> {
        return tteResponse
    }
}
