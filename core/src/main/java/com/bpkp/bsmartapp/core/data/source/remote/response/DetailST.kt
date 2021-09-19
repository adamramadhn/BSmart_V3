package com.bpkp.bsmartapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailST(
    @field:SerializedName("surat_tugas")
    val places2: ArrayList<SuratTugasResponse>,

    val message: String
)
