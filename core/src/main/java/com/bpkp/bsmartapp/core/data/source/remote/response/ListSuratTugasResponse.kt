package com.bpkp.bsmartapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListSuratTugasResponse(

    @field:SerializedName("suratTugas")
    val places: List<SuratTugasResponse>,

)