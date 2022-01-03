package com.bpkp.bsmartapp.core.data.source.remote.response
//Seluruh file pada CORE diperlukan
data class AuthLoginResponse(
    val data_user: List<AuthUserResponse>,
    val rule: List<RuleResponse>,
//    val message: String

)
