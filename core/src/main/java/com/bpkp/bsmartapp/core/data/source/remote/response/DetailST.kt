package com.bpkp.bsmartapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName
//Seluruh file pada CORE diperlukan
data class DetailST(
    @field:SerializedName("surat_tugas")
    val places2: ArrayList<SuratTugasResponse>,

    val message: String
)
