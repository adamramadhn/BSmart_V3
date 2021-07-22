package com.bpkp.bsmartapp.di


import com.bpkp.bsmartapp.login.LoginViewModel
import com.bpkp.bsmartapp.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { LoginViewModel() }
}