package com.nsa.signin.di

import com.nsa.signin.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val featureLoginModule = module {

    scope<LoginViewModel> {
        viewModelOf(::LoginViewModel)
    }
}