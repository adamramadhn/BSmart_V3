package com.bpkp.bsmartapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailST(
    @field:SerializedName("suratTugas")
    val places2: List<SuratTugasResponse>,

    val message: String
)
