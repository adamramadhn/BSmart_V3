package com.bpkp.bsmartapp.pembebanan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bpkp.bsmartapp.core.data.source.remote.network.ApiService
import com.bpkp.bsmartapp.core.data.source.remote.response.pembebanan.PembebananDetail
import com.bpkp.bsmartapp.core.data.source.remote.response.pembebanan.PembebananDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//dipakai
class PembebananDetailViewModel : ViewModel() {
    val detailResponse = MutableLiveData<List<PembebananDetail>?>()
    fun setDetailBeban(idBeban: Int) {
        ApiService().getDetailBeban(idBeban)
            .enqueue(object : Callback<PembebananDetailResponse> {
                override fun onResponse(
                    call: Call<PembebananDetailResponse>,
                    response: Response<PembebananDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        detailResponse.postValue(response.body()?.pembebanan)
                    }
                }

                override fun onFailure(call: Call<PembebananDetailResponse>, t: Throwable) {

                }

            })
    }

    fun getDetailBeban(): LiveData<List<PembebananDetail>?> {
        return detailResponse
    }
}