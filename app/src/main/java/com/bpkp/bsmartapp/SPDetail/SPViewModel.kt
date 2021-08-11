package com.bpkp.bsmartapp.SPDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bpkp.bsmartapp.core.data.source.remote.network.ApiService
import com.bpkp.bsmartapp.core.data.source.remote.response.DetailST
import com.bpkp.bsmartapp.core.data.source.remote.response.SuratTugasResponse
import com.bpkp.bsmartapp.core.data.source.remote.response.sp.ListSpResponse
import com.bpkp.bsmartapp.core.data.source.remote.response.sp.SpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SPViewModel: ViewModel() {

    val dataResponse = MutableLiveData<List<ListSpResponse>?>()
    val loginResponse2 = MutableLiveData<List<SuratTugasResponse>?>()
    fun suratPengantar(
        user_email: String?,
        idSP: Int,
        approval: Int,
        catatan: String?
    ) {
        if (user_email != null && catatan != null) {
            ApiService().approvalSP(user_email, idSP, approval, catatan)
                .enqueue(object : Callback<SpResponse> {
                    override fun onResponse(
                        call: Call<SpResponse>,
                        response: Response<SpResponse>
                    ) {
                        if (response.isSuccessful) {
                            dataResponse.postValue(response.body()?.SuratPengantar)
                        }
                    }

                    override fun onFailure(call: Call<SpResponse>, t: Throwable) {
                    }
                })
        }
    }
//
//    fun getDetail(): LiveData<List<SuratTugasResponse>?> {
//        return loginResponse2
//    }
//
//    fun setDetail(user_email: String, idST: Int) {
//        ApiService().getDetailST(user_email, idST)
//            .enqueue(object : Callback<DetailST> {
//                override fun onResponse(
//                    call: Call<DetailST>,
//                    response: Response<DetailST>
//                ) {
//                    if (response.isSuccessful) {
//                        loginResponse2.postValue(response.body()?.places2)
//                    }
//                }
//
//                override fun onFailure(call: Call<DetailST>, t: Throwable) {
//
//                }
//
//            })
//    }
}