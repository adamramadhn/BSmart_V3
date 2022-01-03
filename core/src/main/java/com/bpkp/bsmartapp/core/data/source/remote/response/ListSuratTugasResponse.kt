package com.bpkp.bsmartapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName
//Seluruh file pada CORE diperlukan
data class ListSuratTugasResponse(

    @field:SerializedName("surat_tugas")
    val places: PagesST,

//    @field:SerializedName("suratTugas")
//    val places: List<SuratTugasResponse>,

)