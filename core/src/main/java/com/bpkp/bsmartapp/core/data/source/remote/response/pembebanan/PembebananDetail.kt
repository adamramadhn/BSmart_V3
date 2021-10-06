package com.bpkp.bsmartapp.core.data.source.remote.response.pembebanan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class PembebananDetail(
    @field:SerializedName("tanggal_update")
    val tgl_buat: String?,
    @field:SerializedName("st_id")
    val id_st: Int?,
    val no_beban: String?,
    val id_beban: Int?,
    val perihal: String?,
    val arsip: String?,
    val untuk: String?,
    val status: Int?,
//mak
    val status_id: Int?,
    val status_detail: String?,
    val id_mak: String?,
    val maknama: String?,
    val makkode: String?,

    val nilai: String?,
    val status_beban: Int?,
//Stepview
    val tanggal_input_pembebanan: String?,
    val tanggal_vp: String?,
    val tanggal_realisasi: String?,
    val tanggal_vr: String?,
    val tanggal_bayar: String?,
//
    val statusbeban: Int?,
)