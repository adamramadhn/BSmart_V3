package com.bpkp.bsmartapp.core.data.source.remote.response.pembebanan
//Seluruh file pada CORE diperlukan
data class PageBeban(
    val current_page: Int,
    val data: List<PembebananData>,

    val from: Int,
    val last_page: Int,
    val to: Int
)