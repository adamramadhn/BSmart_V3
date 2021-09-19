package com.bpkp.bsmartapp.core.data.source.remote.response.pembebanan

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class PembebananResponse(
    val pembebanan: PageBeban,
)