package com.bpkp.bsmartapp.SPDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bpkp.bsmartapp.core.data.source.remote.network.ApiService
import com.bpkp.bsmartapp.core.data.source.remote.response.sp.ListSpResponse
import com.bpkp.bsmartapp.core.data.source.remote.response.sp.SpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//dipakai
class SPViewModel : ViewModel() {

    val dataResponse = MutableLiveData<List<ListSpResponse>?>()
    val loginResponse2 = MutableLiveData<List<ListSpResponse>?>()
    fun suratPengantar(
        user_email: String?,
        idST: Int,
        approval: Int,
        catatan: String?
    ) {
        if (user_email != null && catatan != null) {
            ApiService().approvalSP(user_email, idST, approval, catatan)
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

    fun getSp(): LiveData<List<ListSpResponse>?> {
        return loginResponse2
    }

    fun setSp(idSP: Int) {
        ApiService().getSP(idSP)
            .enqueue(object : Callback<SpResponse> {
                override fun onResponse(
                    call: Call<SpResponse>,
                    response: Response<SpResponse>
                ) {
                    if (response.isSuccessful) {
                        loginResponse2.postValue(response.body()?.SuratPengantar)
                    }
                }

                override fun onFailure(call: Call<SpResponse>, t: Throwable) {

                }

            })
    }
}