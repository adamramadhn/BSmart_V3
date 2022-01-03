package com.bpkp.bsmartapp.core.data.source.remote.response.sp

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
//Seluruh file pada CORE diperlukan
@Parcelize
data class ListSpResponse(
    @field:SerializedName("id")
    val id_sp: Int,

    val st_id: Int,
    val no_sp: String?,
    val tgl_sp: String?,
    val lampiran: String?,
    val hal: String?,
    val yth: String?,
    val di: String?,

    val alinea_1: String?,
    val alinea_2: String?,
    val alinea_3: String?,
    val alinea_4: String?,
    val tembusan: String?,

    val ttd_pegawai_id: Int,

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

    ) : Parcelable
