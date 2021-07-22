package com.bpkp.bsmartapp.core.data.source.remote.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SuratTugas(
    val id_st: String,
    val no_st: String?,
    val perihal: String?,
    val tgl_st: String?,
    val tgl1: String,
    val tgl2: String,
    val user_id: String?,
) : Parcelable