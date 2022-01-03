package com.bpkp.bsmartapp.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bpkp.bsmartapp.core.data.source.remote.network.ApiService
import com.bpkp.bsmartapp.core.data.source.remote.response.DetailST
import com.bpkp.bsmartapp.core.data.source.remote.response.SuratTugasResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//dipakai
class DetailSuratTugasViewModel : ViewModel() {

    val approvalResponse = MutableLiveData<SuratTugasResponse?>()
    val detailResponse = MutableLiveData<List<SuratTugasResponse>?>()
    val tteResponse = MutableLiveData<List<SuratTugasResponse>?>()
    fun approvalST(
        idPegawai: String?, idST: Int?, rule: Int?, approval: Int, catatan: String?
    ) {
        if (idPegawai != null && catatan != null && rule != null && idST != null) {
            ApiService().approvalST(idPegawai, idST, rule, approval, catatan)
                .enqueue(object : Callback<SuratTugasResponse> {
                    override fun onResponse(
                        call: Call<SuratTugasResponse>,
                        response: Response<SuratTugasResponse>
                    ) {
                        if (response.isSuccessful) {
                            approvalResponse.postValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<SuratTugasResponse>, t: Throwable) {
                    }
                })
        }
    }

    fun setDetail(idST: Int) {
        ApiService().getDetailST(idST)
            .enqueue(object : Callback<DetailST> {
                override fun onResponse(
                    call: Call<DetailST>,
                    response: Response<DetailST>
                ) {
                    if (response.isSuccessful) {
                        detailResponse.postValue(response.body()?.places2)
                    }
                }

                override fun onFailure(call: Call<DetailST>, t: Throwable) {

                }

            })
    }

    fun getDetail(): LiveData<List<SuratTugasResponse>?> {
        return detailResponse
    }

    fun setTte(id_st: Int, Nik: String, PassPhrase: String) {
        ApiService().getTte(id_st, Nik, PassPhrase).enqueue(object : Callback<DetailST> {
            override fun onResponse(call: Call<DetailST>, response: Response<DetailST>) {
                tteResponse.postValue(response.body()?.places2)
            }

            override fun onFailure(call: Call<DetailST>, t: Throwable) {

            }

        })
    }

    fun getTte(): LiveData<List<SuratTugasResponse>?> {
        return tteResponse
    }

}
