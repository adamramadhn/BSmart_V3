package com.bpkp.bsmartapp.core.data.source.remote.response
//Seluruh file pada CORE diperlukan
data class PagesST(
    val current_page: Int,
    val data: List<SuratTugasResponse>,

    val from: Int,
    val last_page: Int,
    val to: Int
)
