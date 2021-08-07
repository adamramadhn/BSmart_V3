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

class DetailSuratTugasViewModel: ViewModel() {

    val loginResponse = MutableLiveData<SuratTugasResponse?>()
    val loginResponse2 = MutableLiveData<List<SuratTugasResponse>?>()
    fun suratTugas(
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
                    }
                }

                override fun onFailure(call: Call<DetailST>, t: Throwable) {

                }

            })
    }
}
