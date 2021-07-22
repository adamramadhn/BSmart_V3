package com.bpkp.bsmartapp.core.data.source.remote.response

data class AuthLoginResponse(
    val dataUser: List<AuthUserResponse>,
    val suratTugas: List<SuratTugas>,
    val message: String
)
