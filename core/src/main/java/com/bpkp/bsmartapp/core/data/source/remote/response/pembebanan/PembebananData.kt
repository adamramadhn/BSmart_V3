package com.bpkp.bsmartapp.core.data.source.remote.response.pembebanan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PembebananData(
    @field:SerializedName("tgl")
    val tgl_buat: String?,
    @field:SerializedName("st_id")
    val id_st: Int?,
    val no_beban: String?,
    val id_beban: Int?,
    val perihal: String?,
    val arsip: String?,
    val untuk: String?,
//mak
    val mak_id: Int?,
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
) : Parcelable