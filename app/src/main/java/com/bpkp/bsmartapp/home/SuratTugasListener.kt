 package com.bpkp.bsmartapp.home

import androidx.lifecycle.LiveData
import com.bpkp.bsmartapp.core.data.source.remote.response.SuratTugasResponse

interface SuratTugasListener {
    fun setMessage(message: String)
}