package com.bpkp.bsmartapp.rkd

import androidx.lifecycle.ViewModel
//belum dipakai
class RKDViewModel: ViewModel() {

//    var suratTugasListener: SuratTugasListener? = null
//    val loginResponse = MutableLiveData<List<SuratTugasResponse>>()
//    val cariResponse = MutableLiveData<List<SuratTugasResponse>>()
//
//    fun suratTugas(user_email: String,page:Int) {
//        ApiService().getList(user_email,page).enqueue(object : Callback<ListSuratTugasResponse> {
//            override fun onResponse(
//                call: Call<ListSuratTugasResponse>,
//                response: Response<ListSuratTugasResponse>
//            ) {
//                if (response.isSuccessful) {
//                    loginResponse.postValue(response.body()?.places?.data)
//                }
//            }
//
//            override fun onFailure(call: Call<ListSuratTugasResponse>, t: Throwable) {
//            }
//        })
//    }
//
//    fun getSuratTugas(): LiveData<List<SuratTugasResponse>> {
//        return loginResponse
//    }
//
//    fun setSearchSuratTugas(user_email: String, cari : String): LiveData<List<SuratTugasResponse>> {
//        ApiService().getSearch(user_email,cari)
//            .enqueue(object : Callback<ListSuratTugasResponse> {
//                override fun onFailure(call: Call<ListSuratTugasResponse>, t: Throwable) {
//                }
//
//                override fun onResponse(
//                    call: Call<ListSuratTugasResponse>,
//                    response: Response<ListSuratTugasResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        if(response.body()?.places?.data.isNullOrEmpty()){
//                            suratTugasListener?.setMessage("Tidak ditemukan")
//                            cariResponse.postValue(null)
//                        }else{
//                            cariResponse.postValue(response.body()?.places?.data)
//                        }
//                    }
//                }
//
//            })
//        return cariResponse
//    }
//
//    fun tampilCari(): LiveData<List<SuratTugasResponse>> {
//        return cariResponse
//    }

//    fun suratTugasFilter(user_email: String) {
//        ApiService().getFilter(user_email).enqueue(object : Callback<ListSuratTugasResponse> {
//            override fun onResponse(
//                call: Call<ListSuratTugasResponse>,
//                response: Response<ListSuratTugasResponse>
//            ) {
//                if (response.isSuccessful) {
//                    loginResponse.postValue(response.body()?.places?.data)                }
//            }
//
//            override fun onFailure(call: Call<ListSuratTugasResponse>, t: Throwable) {
//            }
//        })
//    }
}