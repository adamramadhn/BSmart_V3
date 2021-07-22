package com.bpkp.bsmartapp.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bpkp.bsmartapp.core.data.source.remote.network.ApiService
import com.bpkp.bsmartapp.core.data.source.remote.response.ListSuratTugasResponse
import com.bpkp.bsmartapp.core.data.source.remote.response.SuratTugasResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailSuratTugasViewModel {

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
//                            Log.d("ZZZ", "Success boss")
                        }
                    }

                    override fun onFailure(call: Call<SuratTugasResponse>, t: Throwable) {
                        Log.d("ZZZ", "onfailure $t")
                    }
                })
        }
    }

    fun getDetail(): LiveData<List<SuratTugasResponse>?> {
        return loginResponse2
    }

    fun setDetail(user_email: String, idST: Int) {
        ApiService().getDetailST(user_email, idST)
            .enqueue(object : Callback<ListSuratTugasResponse> {
                override fun onResponse(
                    call: Call<ListSuratTugasResponse>,
                    response: Response<ListSuratTugasResponse>
                ) {
                    if (response.isSuccessful) {
                        loginResponse2.postValue(response.body()?.places)
                        Log.d("ZZZ", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<ListSuratTugasResponse>, t: Throwable) {

                }

            })
    }
}
