package com.bpkp.bsmartapp.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SuratTugasResponse(
    @field:SerializedName("id_st")
    val id_st: Int,

    @field:SerializedName("no_st")
    val no_st: String?,

    @field:SerializedName("tgl_st")
    val tgl_st: String?,

    @field:SerializedName("perihal")
    val perihal: String?,

    @field:SerializedName("tgl1")
    val tgl1: String?,

    @field:SerializedName("tgl2")
    val tgl2: String?,

    @field:SerializedName("id")
    val user_id: String?,

    @field:SerializedName("approve_eselon_1")
    val apv_es1: Int?,

    @field:SerializedName("approve_eselon_2")
    val apv_es2: Int?,

    @field:SerializedName("approve_eselon_3")
    val apv_es3: Int?,

    @field:SerializedName("approve_eselon_4")
    val apv_es4: Int?,

    val approve_id_user_eselon_1: String?,
    val approve_id_user_eselon_2: String?,
    val approve_id_user_eselon_3: String?,
    val approve_id_user_eselon_4: String?,

    val review_note_es1: String?,
    val review_note_es2: String?,
    val review_note_es3: String?,
    val review_note_es4: String?,

    val url: String?,
    val created_by: String?,
    val tte: Int?,
    val biaya: String?,

    val jumlahpetugas: Int?,

    val id_jenis_surat: Int?,


    ) : Parcelable