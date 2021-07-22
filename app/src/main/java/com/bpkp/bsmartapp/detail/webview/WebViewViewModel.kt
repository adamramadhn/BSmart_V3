package com.bpkp.bsmartapp.detail.webview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bpkp.bsmartapp.core.data.source.remote.network.ApiService
import com.bpkp.bsmartapp.core.data.source.remote.response.ListSuratTugasResponse
import com.bpkp.bsmartapp.core.data.source.remote.response.PDFResponse
import com.bpkp.bsmartapp.core.data.source.remote.response.SuratTugasResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebViewViewModel : ViewModel() {
    val pdfResponse = MutableLiveData<PDFResponse>()
    fun getPdf(idST: Int) {
        ApiService().getPdf(idST).enqueue(object : Callback<PDFResponse> {
            override fun onResponse(
                call: Call<PDFResponse>,
                response: Response<PDFResponse>
            ) {
                if (response.isSuccessful) {
                    pdfResponse.postValue(response.body())
                    Log.d("ZZZ", response.body().toString())
                }
            }

            override fun onFailure(call: Call<PDFResponse>, t: Throwable) {

            }

        })
    }

    fun getPdf(): LiveData<PDFResponse> {
        return pdfResponse
    }
}