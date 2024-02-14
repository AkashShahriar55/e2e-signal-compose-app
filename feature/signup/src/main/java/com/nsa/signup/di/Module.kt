package com.nsa.signup.di

import com.nsa.signup.RegistrationViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val featureRegistrationModule = module {

    scope<RegistrationViewModel> {
        viewModelOf(::RegistrationViewModel)
    }
}